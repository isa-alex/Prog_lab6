package commands;

import dtp.Request;
import dtp.Response;
import dtp.ResponseStatus;
import exceptions.ExitObligated;
import exceptions.IllegalArguments;

/**
 * Команда 'exit'
 * завершить программу
 */
public class Exit extends Command {
    public Exit(){
        super("exit", ": завершить программу");
    }

    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws ExitObligated нужен выход из программы
     */
    @Override
    public Response execute(Request request) throws IllegalArguments {
        if (!request.getArgs().isBlank()) throw new IllegalArguments();
        return new Response(ResponseStatus.EXIT);
    }
}