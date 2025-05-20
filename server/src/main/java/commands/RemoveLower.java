package commands;

import dtp.Request;
import dtp.Response;
import dtp.ResponseStatus;
import exceptions.ExceptionInFileMode;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.HumanBeing;

import java.util.Collection;
import java.util.Objects;
/**
 * Команда 'remove_lower'
 * Удаляет из коллекции все элементы, меньшие, чем заданный
 */
public class RemoveLower extends Command implements CollectionEditor{
    private CollectionManager collectionManager;

    public RemoveLower(CollectionManager collectionManager) {
        super("remove_lower", " {element} : удалить из коллекции все элементы, меньшие, чем заданный");
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
        class NoElements extends RuntimeException{

        }
        try {
            if (Objects.isNull(request.getObject())) {
                return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
            }
            Collection<HumanBeing> toRemove = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(humanBeing -> humanBeing.compareTo(request.getObject()) < 0)
                    .toList();
            collectionManager.removeElements(toRemove);
            return new Response(ResponseStatus.OK,"Удалены элементы большие чем заданный");
        } catch (NoElements e){
            return new Response(ResponseStatus.ERROR,"В коллекции нет элементов");
        } catch (ExceptionInFileMode e){
            return new Response(ResponseStatus.ERROR,"Поля в файле не валидны! Объект не создан");
        }
    }
}