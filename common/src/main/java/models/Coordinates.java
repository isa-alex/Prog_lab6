package models;

import java.util.Objects;
import java.io.Serializable;

/**
 * Класс координат
 * @author isa-alex
 */
public class Coordinates implements Validator, Serializable {
    private Float x; //Поле не может быть null
    private float y;

    public Coordinates(Float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    /**
     * Валидирует правильность полей
     * @return true если все верно, иначе false
     */
    @Override
    public boolean validate() {
        return !(this.x == null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates that = (Coordinates) obj;
        return Float.compare(that.x, x) == 0 && (y == that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
