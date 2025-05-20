package models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
/**
 * Класс персонажей
 * @author isa-alex
 */
@XStreamAlias("HumanBeing")
public class HumanBeing implements Validator, Comparable<HumanBeing>, Serializable{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private Boolean hasToothpick; //Поле не может быть null
    private float impactSpeed;
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле не может быть null
    private Car car; //Поле может быть null


    public HumanBeing(String name, Coordinates coordinates, Date creationDate, boolean realHero,
                      boolean hasToothpick, float impactSpeed, WeaponType weaponType, Mood mood, Car car) {
        this.id = 0L; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        this.name = name; //Поле не может быть null, Строка не может быть пустой
        this.coordinates = coordinates; //Поле не может быть null
        this.creationDate = creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        this.realHero = realHero;
        this.hasToothpick = hasToothpick; //Поле не может быть null
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType; //Поле может быть null
        this.mood = mood; //Поле не может быть null
        this.car = car; //Поле может быть null
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return  creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isRealHero(){
        return realHero;
    }

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public void setHasToothpick(Boolean hasToothpick){
        this.hasToothpick = hasToothpick;
    }

    public float getImpactSpeed() {
        return impactSpeed;
    }

    public void setImpactSpeed(float impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Компаратор объектов основанный на сравнении скорости
     * @param el the object to be compared.
     */
    @Override
    public int compareTo(HumanBeing el) {
        if (Objects.isNull(el)) return 1;
        return Float.compare(this.impactSpeed, el.impactSpeed);
    }

    /**
     * Метод валидирующий поля по условию
     * @return true если поля валидныеб иначе false
     */
    @Override
    public boolean validate() {
        if (this.id == null || this.id.longValue() < 0L) return false;
        if (this.name == null || this.name.isEmpty()) return false;
        if (this.coordinates == null) return false;
        if (this.creationDate == null) return false;
        if (this.hasToothpick == null) return false;
        if (this.mood == null) return false;
        return true;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        HumanBeing that = (HumanBeing) obj;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!coordinates.equals(that.coordinates)) return false;
        if (!creationDate.equals(that.creationDate)) return false;
        if (realHero != that.realHero) return false;
        if (hasToothpick != that.hasToothpick) return false;
        if (impactSpeed != that.impactSpeed) return false;
        if (weaponType != that.weaponType) return false;
        if (mood != that.mood) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, weaponType, mood, car);
    }

    public static String timeFormatter(Date dateToConvert){
        if (Objects.isNull(dateToConvert)) return "";
        LocalDateTime localDateTime = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        if (localDateTime == null) return null;
        if (localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))){
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    public String toString() {
        return "\n\nHuman Being \nid: " + id + ", " +
                "\nname: " + name + ", " +
                "\ncoordinates: " + coordinates + ", " +
                "\ncreationDate: " + creationDate + ", " +
                "\nreal hero: " + realHero + ", " +
                "\nhas tooth pick: " + hasToothpick + ", " +
                "\nimpact speed: " + impactSpeed + ", " +
                "\nweapon type: " + weaponType + ", " +
                "\nmood: " + mood + ", " +
                "\ncar: " + (car == null ? "no car" : car.getName());
    }
}
