package commands;

import dtp.Request;
import dtp.Response;
import dtp.ResponseStatus;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.HumanBeing;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PrintAscending extends Command {
    private final CollectionManager collectionManager;

    public PrintAscending(CollectionManager collectionManager) {
        super("print_asc_mood",
                " : вывести значения поля mood всех элементов в порядке возрастания (ухудшения настроения)");
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

        List<HumanBeing> sortedByMood = collection.stream()
                .filter(h -> h.getMood() != null)
                .sorted((h1, h2) -> h1.getMood().compareTo(h2.getMood()))
                .collect(Collectors.toList());

        if (sortedByMood.isEmpty()) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS, "В коллекции нет персонажей с таким настроением");
        }

        StringBuilder output = new StringBuilder();
        for (HumanBeing h : sortedByMood) {
            output.append(h.getName()).append(": ").append(h.getMood()).append("\n");
        }

        output.append("\nВсего значений: ").append(sortedByMood.size()).append("\n");
        output.append("Уникальных настроений: ")
                .append(sortedByMood.stream().map(HumanBeing::getMood).distinct().count());

        return new Response(ResponseStatus.OK, output.toString());
    }
}