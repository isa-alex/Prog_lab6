package commands;

import dtp.Request;
import dtp.Response;
import dtp.ResponseStatus;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterGreater extends Command {
    private final CollectionManager collectionManager;

    public FilterGreater(CollectionManager collectionManager) {
        super("filt_gr_weapon",
                " weaponType : вывести персонажей, у которых оружие круче заданного");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArguments {
        if (request.getArgs().isBlank()) {
            throw new IllegalArguments();
        }

        try {
            WeaponType inputWeaponType = WeaponType.valueOf(request.getArgs().trim().toUpperCase());

            LinkedHashSet<HumanBeing> collection = collectionManager.getCollection();

            Set<HumanBeing> filtered = collection.stream()
                    .filter(human -> human.getWeaponType() != null)
                    .filter(human -> human.getWeaponType().ordinal() > inputWeaponType.ordinal())
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            if (filtered.isEmpty()) {
                return new Response(ResponseStatus.OK, "Нет персонажей с оружием круче чем " + inputWeaponType);
            } else {
                StringBuilder result = new StringBuilder();
                result.append("Персонажи с оружием круче ").append(inputWeaponType).append(":\n");

                filtered.forEach(human ->
                        result.append(human.getName())
                                .append(": оружие - ")
                                .append(human.getWeaponType())
                                .append("\n"));

                return new Response(ResponseStatus.OK, result.toString());
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArguments();
        }
    }
}