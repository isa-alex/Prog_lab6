package models;

import java.io.Serializable;

/**
 * Класс машины
 * @author isa-alex
 */
public class Car implements Serializable {
    private String name; //Поле может быть null

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (name == null) {
            return "no car";
        }
        return name;
    }
}
