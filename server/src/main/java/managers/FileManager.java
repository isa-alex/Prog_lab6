package managers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.AnyTypePermission;
import exceptions.ExitObligated;
import exceptions.InvalidForm;
import models.HumanBeing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilty.Printable;


import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Класс реализующий работу с файлами
 * @author isa_alex
 */
public class FileManager {
    private String text;
    private final Printable console;
    private final CollectionManager collectionManager;
    private final XStream xStream = new XStream();

    static final Logger fileManagerLogger = LogManager.getLogger(FileManager.class);

    /**
     * В конструкторе задаются alias для библиотеки {@link XStream} которая используется для работы с xml
     * @param console Пользовательский ввод-вывод
     * @param collectionManager Работа с коллекцией
     */
    public FileManager(Printable console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;

        this.xStream.alias("HumanBeing", HumanBeing.class);
        this.xStream.alias("LinkedHashSet", CollectionManager.class);
        this.xStream.addPermission(AnyTypePermission.ANY);
        this.xStream.addImplicitCollection(CollectionManager.class, "collection");
        fileManagerLogger.info("Alias for xstream had been created");
    }

    /**
     * Обращение к переменным среды и чтение файла в поле по указанному пути
     * @throws ExitObligated если путь - null или отсутствует, программа заканчивает выполнение
     */
    public void findFile() throws ExitObligated{
        String file_path = System.getenv("file_path");
        if (file_path == null || file_path.isEmpty()) {
            console.printError("Path has to be in environmental variable named 'file_path'");
            throw new ExitObligated();
        }
        else console.println("Path had been successfully gotten");
        fileManagerLogger.info("Path had been successfully gotten");

        File file = new File(file_path);
        BufferedInputStream bis;
        FileInputStream fis;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            fileManagerLogger.info("Connection with the file had been opened");
            while (bis.available() > 0) {
                stringBuilder.append((char) bis.read());
            }
            fis.close();
            bis.close();
            fileManagerLogger.info("File had been read");
            if (stringBuilder.isEmpty()) {
                console.printError("File is empty");
                fileManagerLogger.info("File is empty");
                this.text = "</LinkedHashSet>";
                return;
            }
            this.text = stringBuilder.toString();
        } catch (FileNotFoundException fnfe) {
            console.printError("There is no such file");
            fileManagerLogger.fatal("There is no such file");
            throw new ExitObligated();
        } catch (IOException ioe) {
            console.printError("input/output error" + ioe);
            fileManagerLogger.fatal("input/output error" + ioe);
            throw new ExitObligated();
        }
    }

    /**
     * Создание объектов в консольном менеджере
     * @throws ExitObligated Если объекты в файле невалидны выходим из программы
     */
    public void createObjects() throws ExitObligated{
        try{
            XStream xstream = new XStream();
            xstream.alias("HumanBeing", HumanBeing.class);
            xstream.alias("LinkedHashSet", CollectionManager.class);
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.addImplicitCollection(CollectionManager.class, "collection");

            fileManagerLogger.info("xstream configuration for reading from file had been made");
            CollectionManager collectionManagerWithObjects = (CollectionManager) xstream.fromXML(this.text);
            fileManagerLogger.info("File had been read", collectionManagerWithObjects.getCollection());

            for(HumanBeing s : collectionManagerWithObjects.getCollection()){
                if (this.collectionManager.checkExist(s.getId())){
                    console.printError("Repeated id in file");
                    throw new ExitObligated();
                }
                if (!s.validate()) throw new InvalidForm();
                this.collectionManager.addElement(s);
                fileManagerLogger.info("Added object", s);
            }

        } catch (InvalidForm invalidForm) {
            console.printError("Objects in file are invalid");
            fileManagerLogger.fatal("Objects in file are invalid");
            throw new ExitObligated();
        } catch (StreamException streamException){
            console.println("File is empty");
            fileManagerLogger.error("File is empty");
        }
        console.println("Had been gotten objects:\n" + collectionManager.getCollection().toString());
        CollectionManager.updateId(collectionManager.getCollection());
    }

    /**
     * Сохраняем коллекцию из менеджера в файл
     */
    public void saveObjects(){
        String file_path = System.getenv("file_path");
        if (file_path == null || file_path.isEmpty()) {
            console.printError("Path has to be in environmental variable named  'file_path'");
            fileManagerLogger.fatal("There is no path in environmental variable"); }
        else {
            console.println("Path had been gotten successfully");
            fileManagerLogger.info("Path has to be in environmental variable named ");
        }
        try{
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file_path));
            out.write(this.xStream.toXML(collectionManager)
                    .getBytes(StandardCharsets.UTF_8));
            out.close();
            fileManagerLogger.info("File had been written");
        } catch (FileNotFoundException e) {
            console.printError("File does not exist");
            fileManagerLogger.error("File does not exist");
        }catch (IOException e){
            console.printError("input/output error");
            fileManagerLogger.error("input/output error");
        }
    }
}