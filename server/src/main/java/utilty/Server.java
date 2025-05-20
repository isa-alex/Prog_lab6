package utilty;

import dtp.Request;
import dtp.Response;
import exceptions.ConnectionErrorException;
import exceptions.OpeningServerException;
import managers.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


public class Server {
    private int port;
    private int soTimeout;
    private Printable console;
    private ServerSocketChannel ss;
    private SocketChannel socketChannel;
    private RequestHandler requestHandler;

    static final Logger serverLogger = LogManager.getLogger(Server.class);

    BufferedInputStream bf = new BufferedInputStream(System.in);

    BufferedReader scanner = new BufferedReader(new InputStreamReader(bf));
    private FileManager fileManager;

    public Server(int port, int soTimeout, RequestHandler handler, FileManager fileManager) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.console = new BlankConsole();
        this.requestHandler = handler;
        this.fileManager = fileManager;
    }

    public void run(){
        try{
            openServerSocket();
            serverLogger.info("Successfully connected with a client");
            while (true) {
                try {
                    if (scanner.ready()) {
                        String line = scanner.readLine();
                        if (line.equals("save") || line.equals("s")) {
                            fileManager.saveObjects();
                            serverLogger.info("Collection has been saved");
                        }
                    }
                } catch (IOException ignored) {}
                try (SocketChannel clientSocket = connectToClient()) {
                    if(clientSocket == null) continue;
                    clientSocket.configureBlocking(false);
                    if(!processClientRequest(clientSocket)) break;
                } catch (ConnectionErrorException | SocketTimeoutException ignored) {
                } catch (IOException exception) {
                    console.printError("An error appeared when trying to close connection with a client");
                    serverLogger.error("An error appeared when trying to close connection with a client");
                }
            }
            stop();
            serverLogger.info("Connection closed");
        } catch (OpeningServerException e) {
            console.printError("Server can't be runed");
            serverLogger.fatal("Server can't be runed");
        }
    }

    private void openServerSocket() throws OpeningServerException{
        try {
            SocketAddress socketAddress = new InetSocketAddress(port);
            serverLogger.debug("Socket created");
            ss = ServerSocketChannel.open();
            serverLogger.debug("Channel created");
            ss.bind(socketAddress);
            ss.configureBlocking(false);
            serverLogger.debug("Socket cannel opened");
        } catch (IllegalArgumentException exception) {
            console.printError("Port '" + port + "' is out of reach");
            serverLogger.error("Port is out of reach");
            throw new OpeningServerException();
        } catch (IOException exception) {
            serverLogger.error("An error appeared when trying to use port");
            console.printError("An error appeared when trying to use port '" + port + "'!");
            throw new OpeningServerException();
        }
    }

    private SocketChannel connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try {
            socketChannel = ss.accept();
            return socketChannel;
        } catch (SocketTimeoutException exception) {
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            serverLogger.fatal("An error appeared when trying to connect with a client");
            throw new ConnectionErrorException();
        }
    }

    private boolean processClientRequest(SocketChannel clientSocket) {
        Request userRequest = null;
        Response responseToUser = null;
        try {
            Request request = getSocketObjet(clientSocket);
            serverLogger.info("Got a request with a command " + request.getCommandName(), userRequest);
            console.println(request.toString());
            responseToUser = requestHandler.handle(request);
            sendSocketObject(clientSocket, responseToUser);
            serverLogger.info("Response sent", responseToUser);
        } catch (ClassNotFoundException exception) {
            console.printError("An error appeared when trying to read gotten data");
            serverLogger.fatal("An error appeared when trying to read gotten data");
        } catch (InvalidClassException | NotSerializableException exception) {
            console.printError("An error appeared when trying to sent data to client");
            serverLogger.error("An error appeared when trying to sent data to client");
        } catch (IOException exception) {
            if (userRequest == null) {
                console.printError("Unexpected cancelling of connection with a client");
                serverLogger.error("Unexpected cancelling of connection with a client");
            } else {
                console.println("Client successfully disconnected from server");
                serverLogger.info("Client successfully disconnected from server");
            }
        }
        return true;
    }

    public static Request getSocketObjet(SocketChannel channel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(1024*10);
        while (true) {
            try {
                channel.read(buffer);
                buffer.mark();
                byte[] buf = buffer.array();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                return (Request) objectInputStream.readObject();
            } catch (StreamCorruptedException e) {
                // if we cannot readObject we haven't read yet, so try another time
            }
        }
    }

    private static void sendSocketObject(SocketChannel client, Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
        client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
    }

    private void stop() {
        class ClosingSocketException extends Exception{}
        try{
            if (socketChannel == null) throw new ClosingSocketException();
            socketChannel.close();
            ss.close();
            serverLogger.info("all connections closed");
        } catch (ClosingSocketException exception) {
            console.printError("Can't close an unopened server");
            serverLogger.fatal("Can't close an unopened server");
        } catch (IOException exception) {
            console.printError("An error appeared when closing a server");
            serverLogger.fatal("An error appeared when closing a server");
        }
    }
}