package commandLine.forms;

import commandLine.*;
import exceptions.ExceptionInFileMode;
import utilty.ExecuteFileManager;
import models.Mood;

/**
 * Форма для настроения
 */
public class MoodForm extends Form<Mood>{
    private final Printable console;
    private final UserInput scanner;

    public MoodForm(Printable console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Сконструировать новый элемент Enum {@link Mood}
     * @return объект Enum {@link Mood}
     */
    @Override
    public Mood build() {
        console.println("Доступные состояния:");
        console.println(Mood.names());

        while (true) {
            console.print("Выберете состояние персонажа: ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return Mood.valueOf(input);
            } catch (IllegalArgumentException e) {
                console.printError("Такое состояние недоступно. Повторите ввод.");
                if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }
}
