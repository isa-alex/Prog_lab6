package models;

import java.io.Serializable;

/**
 * Возможные виды оружия
 * @author isa-alex
 */
public enum WeaponType implements Serializable {
    HAMMER,
    SHOTGUN,
    RIFLE,
    KNIFE,
    BAT;

    /**
     * @return перечисляет по строкам все элементы Enum
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var weaponType : values()) {
            nameList.append(weaponType.name()).append("\n");
        }
        return nameList.substring(0, nameList.length() - 1);
    }

    public boolean isGreaterThan(WeaponType other) {
        return this.ordinal() > other.ordinal();
    }
}
