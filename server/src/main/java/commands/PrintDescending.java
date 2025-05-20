package commands;

import dtp.Request;
import dtp.Response;
import dtp.ResponseStatus;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.HumanBeing;

import java.util.LinkedHashSet;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PrintDescending extends Command {
    private final CollectionManager collectionManager;

    public PrintDescending(CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArguments {
        if (!request.getArgs().isBlank()) {
            throw new IllegalArguments();
        }

        LinkedHashSet<HumanBeing> collection = collectionManager.getCollection();

        if (collection.isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Коллекция пуста");
        }

        Comparator<HumanBeing> descendingComparator = (h1, h2) -> Long.compare(h2.getId(), h1.getId());

        List<HumanBeing> sortedList = collection.stream()
                .sorted(descendingComparator)
                .collect(Collectors.toList());

        // Собираем результат в StringBuilder
        StringBuilder result = new StringBuilder();
        result.append("Персонажи в обратном порядке добавления:\n");

        sortedList.forEach(human -> {
            result.append(human.getName()).append("\n");
        });

        return new Response(ResponseStatus.OK, result.toString());
    }
}