package commandLine;

import exceptions.CommandRunTimeError;
import exceptions.ExitObligated;
import exceptions.IllegalArguments;

/**
 * Интерфейс для исполняемых команд
 */
public interface Executable {
    void execute(String args) throws CommandRunTimeError, ExitObligated, IllegalArguments;
}
