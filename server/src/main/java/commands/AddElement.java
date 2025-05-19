package commands;

import dtp.Request;
import dtp.Response;
import dtp.ResponseStatus;
import exceptions.IllegalArguments;
import managers.CollectionManager;

import java.util.Objects;

/**
 * команда 'add {element}'
 * добавить новый элемент с заданным ключом
 */
public class AddElement extends Command implements CollectionEditor {
    private final CollectionManager collectionManager;

    public AddElement(CollectionManager collectionManager) {
        super("add", " {element}: добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArguments неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArguments {
        if (!request.getArgs().isBlank()) throw new IllegalArguments();
        if (Objects.isNull(request.getObject())) {
            return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        } else {
            request.getObject().setId(CollectionManager.getNextId());
            collectionManager.addElement(request.getObject());
            return new Response(ResponseStatus.OK, "Персонаж успешно добавлен");
        }
    }
}
