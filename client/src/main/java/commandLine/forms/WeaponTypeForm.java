package commandLine.forms;

import commandLine.*;
import models.WeaponType;
import utilty.ExecuteFileManager;

/**
 * Форма для видов оружия
 */
public class WeaponTypeForm extends Form<WeaponType>{
    private final Printable console;
    private final UserInput scanner;

    public WeaponTypeForm(Printable console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Сконструировать новый элемент Enum {@link WeaponType}
     * @return объект Enum {@link WeaponType}
     */
    @Override
    public WeaponType build() {
        console.println("Доступное оружие:");
        console.println(WeaponType.names());


        while (true) {
            console.print("Выберете оружие: ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return WeaponType.valueOf(input);
            } catch (IllegalArgumentException e) {
                console.println("Такое оружие не доступно. Повторите ввод.");
            }
        }
    }
}
