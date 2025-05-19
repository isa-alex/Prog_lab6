package commands;

import dtp.Request;
import dtp.Response;
import exceptions.CommandRunTimeError;
import exceptions.ExitObligated;
import exceptions.IllegalArguments;

/**
 * Интерфейс для исполняемых команд
 */
public interface Executable {
    Response execute(Request request) throws CommandRunTimeError, ExitObligated, IllegalArguments;
}