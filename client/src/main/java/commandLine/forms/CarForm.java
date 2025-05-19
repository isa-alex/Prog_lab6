package commandLine.forms;

import commandLine.*;
import models.Car;
import utilty.ExecuteFileManager;


/**
 * Форма для модели Car
 */
public class CarForm extends Form<Car>{
    private final Printable console;
    private final UserInput scanner;

    public CarForm(Printable console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }


    /**
     * Сконструировать новый элемент Car {@link Car}
     * @return объект Car {@link Car}
     */
    @Override
    public Car build() {
        return new Car(askName());
    }

    private String askName() {
        while (true) {
            console.print("Есть ли машина? (Название если есть | no если машины нет): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("no")) return "no car";
            return input;
        }
    }
}