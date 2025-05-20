import exceptions.ExitObligated;
import managers.*;

import utilty.*;
import commands.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App extends Thread {
    public static int PORT = 6086;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    private static final Printable console = new BlankConsole();

    static final Logger rootLogger = LogManager.getRootLogger();

    public static void main(String[] args) {
        if(args.length != 0){
            try{
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {}
        }
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(console, collectionManager);
        try{
            App.rootLogger.info("Objects creation");
            fileManager.findFile();
            fileManager.createObjects();
            App.rootLogger.info("Objects had been created Successfully");
        } catch (ExitObligated e){
            console.println("See you soon");
            App.rootLogger.error("Objects creation error");
            return;
        }

        CommandManager commandManager = new CommandManager(fileManager);
        commandManager.addCommand(List.of(
                new Help(commandManager),
                new Info(collectionManager),
                new Show(collectionManager),
                new AddElement(collectionManager),
                new Update(collectionManager),
                new RemoveById(collectionManager),
                new Clear(collectionManager),
                new ExecuteScript(),
                new Exit(),
                new AddIfMax(collectionManager),
                new RemoveLower(collectionManager),
                new History(commandManager),
                new FilterGreater(collectionManager),
                new PrintDescending(collectionManager),
                new PrintAscending(collectionManager)
        ));
        App.rootLogger.debug("Command manager object had been created");
        RequestHandler requestHandler = new RequestHandler(commandManager);
        App.rootLogger.debug("Request manager object had been crated");
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler, fileManager);
        App.rootLogger.debug("Server object had been created");
        server.run();
    }
}