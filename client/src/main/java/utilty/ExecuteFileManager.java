package utilty;

import commandLine.UserInput;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * Класс для хранения файл менеджера для команды execute
 */
public class ExecuteFileManager implements UserInput {
    private static final LinkedHashSet<String> pathQueue = new LinkedHashSet<>();
    private static final LinkedHashSet<BufferedReader> fileReaders = new LinkedHashSet<>();

    public static void pushFile(String path) throws FileNotFoundException{
        pathQueue.add(new File(path).getAbsolutePath());
        fileReaders.add(new BufferedReader(new InputStreamReader(new FileInputStream(path))));
    }

    public static File getFile() {
        return new File(Objects.requireNonNull(pathQueue.stream()
                .skip(pathQueue.size() - 1)
                .findFirst()
                .orElse(null)));
    }

    public static String readLine() throws IOException {
        return fileReaders.stream().findFirst().get().readLine();
    }

    public static void popFile() throws IOException {
        if (!fileReaders.isEmpty()) {
            BufferedReader reader = fileReaders.iterator().next();
            reader.close();
            fileReaders.remove(reader);
        }
        if (!pathQueue.isEmpty()) {
            String path = pathQueue.iterator().next();
            pathQueue.remove(path);
        }
    }

    public static void popRecursion() {
        if (!pathQueue.isEmpty()) {
            String path = pathQueue.iterator().next();
            pathQueue.remove(path);
        }
    }

    public static boolean fileRepeat(String path){
        return pathQueue.contains(new File(path).getAbsolutePath());
    }

    @Override
    public String nextLine() {
        try{
            return readLine();
        } catch (IOException e){
            return "";
        }
    }
}