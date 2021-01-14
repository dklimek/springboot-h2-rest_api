package com.klimek.cars.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Cars {

    @Id
    private Long id;
    private String mark;
    private String colour;
    private String yearOfProduction;
    private String peopleCapacity;
    private String numberOfDoors;
    private String gearbox;
    private boolean rented;

    public Cars() {
    }

    public Cars(Long id, String mark) {
        this.id = id;
        this.mark = mark;
    }

    public Cars(Long id, String mark, String colour, String yearOfProduction, String peopleCapacity, String numberOfDoors, String gearbox) {
        this.id = id;
        this.mark = mark;
        this.colour = colour;
        this.yearOfProduction = yearOfProduction;
        this.peopleCapacity = peopleCapacity;
        this.numberOfDoors = numberOfDoors;
        this.gearbox = gearbox;
    }

    public Cars(Long id, String mark, String colour, String yearOfProduction, String peopleCapacity, String numberOfDoors, String gearbox, boolean rented) {
        this.id = id;
        this.mark = mark;
        this.colour = colour;
        this.yearOfProduction = yearOfProduction;
        this.peopleCapacity = peopleCapacity;
        this.numberOfDoors = numberOfDoors;
        this.gearbox = gearbox;
        this.rented = rented;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getPeopleCapacity() {
        return peopleCapacity;
    }

    public void setPeopleCapacity(String peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }

    public String getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(String numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    @Override
    public String toString() {
        return "Cars [id=" + id + ", colour=" + colour + ", yearOfProduction=" + yearOfProduction + ", peopleCapacity=" + peopleCapacity + ", numberOfDoors=" + numberOfDoors +
                ", gearbox=" + gearbox + ", rented=" + rented + "]";
    }

}
