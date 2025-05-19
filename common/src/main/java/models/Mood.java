package models;

import java.io.Serializable;

/**
 * Возможные настроения
 * @author isa-alex
 */
public enum Mood implements Serializable {
    SADNESS,
    SORROW,
    CALM,
    RAGE,
    FRENZY;

    /**
     * @return перечисляет по строкам все элементы Enum
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var moods : values()) {
            nameList.append(moods.name()).append("\n");
        }
        return nameList.substring(0, nameList.length() - 1);
    }
}
