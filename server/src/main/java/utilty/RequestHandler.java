package utilty;

import dtp.Request;
import dtp.Response;
import dtp.ResponseStatus;
import exceptions.CommandRunTimeError;
import exceptions.ExitObligated;
import exceptions.IllegalArguments;
import exceptions.NoSuchCommand;
import managers.CommandManager;

public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response handle(Request request) {
        try {
            commandManager.addToHistory(request.getCommandName());
            return commandManager.execute(request);
        } catch (IllegalArguments e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,
                    "Неверное использование аргументов команды");
        } catch (CommandRunTimeError e) {
            return new Response(ResponseStatus.ERROR,
                    "Ошибка при исполнении программы");
        } catch (NoSuchCommand e) {
            return new Response(ResponseStatus.ERROR, "Такой команды нет в списке");
        } catch (ExitObligated e) {
            return new Response(ResponseStatus.EXIT);
        }
    }
}
