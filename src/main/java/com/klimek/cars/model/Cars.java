package com.klimek.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
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

    @Override
    public String toString() {
        return "Cars [id=" + id + ", colour=" + colour + ", yearOfProduction=" + yearOfProduction + ", peopleCapacity=" + peopleCapacity + ", numberOfDoors=" + numberOfDoors +
                ", gearbox=" + gearbox + ", rented=" + rented + "]";
    }


}
