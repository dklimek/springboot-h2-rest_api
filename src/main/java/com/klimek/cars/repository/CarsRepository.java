package com.klimek.cars.repository;

import com.klimek.cars.exception.ProductNotRentException;
import com.klimek.cars.exception.ProductNotfoundException;
import com.klimek.cars.exception.ProductRentedException;
import com.klimek.cars.model.Cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.klimek.cars.model.CarsConstants.*;

@Repository
public class CarsRepository {

    @Autowired
    JdbcTemplate template;

    /* Getting all */
    public List<Cars> getAllCars() {
        try {
            return template.query("select * from CARS", (result,
                                                         rowNum) -> new Cars(
                    result.getLong(CARS_ID),
                    result.getString(CARS_MARK),
                    result.getString(CARS_COLOUR),
                    result.getString(CARS_YEAR_OF_PRODUCTION),
                    result.getString(CARS_PEOPLE_CAPACITY),
                    result.getString(CARS_NUMBER_OF_DOORS),
                    result.getString(CARS_GEARBOX),
                    result.getBoolean(CARS_RENTED)
            ));
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotfoundException();
        }
    }
    /* Getting all name by query string */

    public List<Cars> getCarsToRent() {
        try {
            return template.query("SELECT * FROM CARS WHERE RENTED=false", (result,
                                                                            rowNum) -> new Cars(
                    result.getLong(CARS_ID),
                    result.getString(CARS_MARK),
                    result.getString(CARS_COLOUR),
                    result.getString(CARS_YEAR_OF_PRODUCTION),
                    result.getString(CARS_PEOPLE_CAPACITY),
                    result.getString(CARS_NUMBER_OF_DOORS),
                    result.getString(CARS_GEARBOX),
                    result.getBoolean(CARS_RENTED)
            ));
        }catch (EmptyResultDataAccessException e){
            throw new ProductNotfoundException();
        }
    }

    /* Getting by id */
    public Cars getCar(int search) {
        try {
            String query = "SELECT * FROM CARS WHERE ID =?";
            return template.queryForObject(query, (result,
                                                   rowNum) -> new Cars(
                    result.getLong(CARS_ID),
                    result.getString(CARS_MARK),
                    result.getString(CARS_COLOUR),
                    result.getString(CARS_YEAR_OF_PRODUCTION),
                    result.getString(CARS_PEOPLE_CAPACITY),
                    result.getString(CARS_NUMBER_OF_DOORS),
                    result.getString(CARS_GEARBOX),
                    result.getBoolean(CARS_RENTED)
            ),search);
        }catch (EmptyResultDataAccessException e){
            throw new ProductNotfoundException();
        }

    }

    /* Adding into database table */
    public int addCar( String mark, String colour, String yearOfProduction, String peopleCapacity,String numberOfDoors, String gearbox, Boolean rented) {
        String query = "INSERT INTO CARS (  mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented) VALUES(?,?,?,?,?,?,?)";
        return template.update(query, mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented);
    }
    public int addCar( Cars car) {
        String query = "INSERT INTO CARS (  mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented) VALUES(?,?,?,?,?,?,?)";
        return template.update(query, car.getMark(), car.getColour(), car.getYearOfProduction(), car.getPeopleCapacity(), car.getNumberOfDoors(), car.getGearbox(),car.isRented());
    }

    /** 
     * update a car or insert a car if that ID doesn't exist
    */
    public int updateCar(int id, String mark, String colour, String yearOfProduction, String peopleCapacity,String numberOfDoors, String gearbox, Boolean rented) {
        String checkid = "SELECT * FROM CARS WHERE ID=?1";
        String insertion = "INSERT INTO CARS (mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented) VALUES(?2,?3,?4,?5,?6,?7,?8)";
        String updated = "UPDATE  CARS SET mark =?2,colour =?3,yearOfProduction =?4,peopleCapacity =?5,numberOfDoors =?6,gearbox =?7 WHERE ID =?1";
        try {
            Cars items = template.queryForObject(checkid, (result, rowNum) -> new Cars(
                    result.getLong(CARS_ID),
                    result.getString(CARS_MARK),
                    result.getString(CARS_COLOUR),
                    result.getString(CARS_YEAR_OF_PRODUCTION),
                    result.getString(CARS_PEOPLE_CAPACITY),
                    result.getString(CARS_NUMBER_OF_DOORS),
                    result.getString(CARS_GEARBOX),
                    result.getBoolean(CARS_RENTED)
            ), id);
            template.update(updated, id, mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox);
            return id;
        } catch (EmptyResultDataAccessException e)
        {
            return template.update(insertion, id, mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented);
        }


       
    }
    /**
     *  get the last car
     */
    public Cars lastCar() {
        try{
            return template.queryForObject("SELECT * from CARS WHERE id=(SELECT max(id) FROM CARS)", (result,
                                                                                                      rowNum) -> new Cars(
                    result.getLong(CARS_ID),
                    result.getString(CARS_MARK),
                    result.getString(CARS_COLOUR),
                    result.getString(CARS_YEAR_OF_PRODUCTION),
                    result.getString(CARS_PEOPLE_CAPACITY),
                    result.getString(CARS_NUMBER_OF_DOORS),
                    result.getString(CARS_GEARBOX),
                    result.getBoolean(CARS_RENTED)
            ));
        }catch (EmptyResultDataAccessException e) {
            throw new ProductNotfoundException();
        }
    }

    /* Delete an item */
    public int deleteItem(int id) {
        String query = "DELETE FROM CARS WHERE ID =?";
        return template.update(query, id);
    }
    /**
     *  rent the car with id
     */
    public int rentCar(int id) {
        String checkid = "SELECT * FROM CARS WHERE ID=?1 AND RENTED=false";
        String updated = "UPDATE  CARS SET rented=true WHERE ID =?1";
        try{
            Cars items = template.queryForObject(checkid, (result, rowNum) -> new Cars(
                    result.getLong(CARS_ID),
                    result.getString(CARS_MARK),
                    result.getString(CARS_COLOUR),
                    result.getString(CARS_YEAR_OF_PRODUCTION),
                    result.getString(CARS_PEOPLE_CAPACITY),
                    result.getString(CARS_NUMBER_OF_DOORS),
                    result.getString(CARS_GEARBOX),
                    result.getBoolean(CARS_RENTED)
            ), id);
            template.update(updated, id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            throw new ProductRentedException();
        }
    }
    /**
     *  return the car with id
     */
    public int returnCar(int id) {
        String checkid = "SELECT * FROM CARS WHERE ID=?1 AND RENTED=true ";
        String updated = "UPDATE  CARS SET rented=false WHERE ID =?1";
        try {
            Cars items = template.queryForObject(checkid, (result, rowNum) -> new Cars(
                    result.getLong(CARS_ID),
                    result.getString(CARS_MARK),
                    result.getString(CARS_COLOUR),
                    result.getString(CARS_YEAR_OF_PRODUCTION),
                    result.getString(CARS_PEOPLE_CAPACITY),
                    result.getString(CARS_NUMBER_OF_DOORS),
                    result.getString(CARS_GEARBOX),
                    result.getBoolean(CARS_RENTED)
            ), id);
            template.update(updated, id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotRentException();
        }


    }
}
