package utilty;

/**
 * Класс для вывода в консоль
 */
public class Console implements Printable {
    private static boolean fileMode = false;

    public static boolean isFileMode() {
       return fileMode;
    }

    public static void setFileMode(boolean fileMode) {
        Console.fileMode = fileMode;
    }

    @Override
    public void println(String a) {
        System.out.println(a);
    }

    @Override
    public void print(String a){
        System.out.print(a);
    }

    @Override
    public void printError(String a) {
        System.out.println(a);
    }
}
