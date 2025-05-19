package commandLine;

/**
 * Интерфейс со способами вывода
 */
public interface Printable {
    void println(String a);
    void print(String a);
    void printError(String a);
}
