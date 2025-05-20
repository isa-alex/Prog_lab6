package commandLine.forms;

import commandLine.*;
import models.*;
import exceptions.ExceptionInFileMode;
import utilty.ExecuteFileManager;

import java.util.Date;

/**
 * Форма персонажей
 * @author isa-alex
 */
public class HumanBeingForm extends Form<HumanBeing> {
    private final Printable console;
    private final UserInput scanner;

    public HumanBeingForm(Printable console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }


    /**
     * Сконструировать новый элемент класса {@link HumanBeing}
     * @return объект класса {@link HumanBeing}
     */
    @Override
    public HumanBeing build() {
        return new HumanBeing(
            askName(),
            askCoordinates(),
            new Date(),
            askRealHero(),
            askHasToothpick(),
            askImpactSpeed(),
            askWeaponType(),
            askMood(),
            askCar()
        );
    }

    private String askName() {
        String name;
        while (true) {
            console.println("Введите имя персонажа ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                console.printError("Персонаж должен иметь имя, введите имя ");
                if (Console.isFileMode()) throw new ExceptionInFileMode();
            } else return name;
        }
    }

    private Coordinates askCoordinates() {
        return new CoordinatesForm(console).build();
    }

    private boolean askRealHero() {
        while (true) {
            console.print("Он:а настоящий герой? (yes | no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) return true;
            if (input.equals("no")) return false;
            console.println("Введите 'yes' или 'no'.");
        }
    }

    private boolean askHasToothpick() {
        while (true) {
            console.print("Есть ли зубочистка? (yes | no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) return true;
            if (input.equals("no")) return false;
            console.println("Введите 'yes' или 'no'.");
        }
    }

    private float askImpactSpeed() {
        while (true) {
            console.print("Введите скорость удара: ");
            try {
                return Float.parseFloat(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                console.println("Скорость удара должна быть числом, введите число: ");
            }
        }
    }

    private WeaponType askWeaponType() {
        return new WeaponTypeForm(console).build();
    }

    private Mood askMood() {
        return new MoodForm(console).build();
    }

    private Car askCar() {
        return new CarForm(console).build();
    }
}

