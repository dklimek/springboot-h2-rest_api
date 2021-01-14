package com.klimek.cars.controller;

import java.util.List;

import com.klimek.cars.api.ApiConstants;
import com.klimek.cars.exception.ProductNotRentException;
import com.klimek.cars.exception.ProductNotfoundException;
import com.klimek.cars.exception.ProductRentedException;
import com.klimek.cars.model.Cars;
import com.klimek.cars.repository.CarsRepository;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.klimek.cars.api.ApiConstants.*;
import static com.klimek.cars.model.CarsConstatns.*;
@RequestMapping(API_PATH)
@RestController
public class CarsController {

    @Autowired
    CarsRepository carRepo;

    @GetMapping(CARS_GET_ALL_PATH)
    @ResponseBody
    public List<Cars> getAll() {
            return carRepo.getAllCars();
    }

    @PostMapping(value = CARS_GET_ALL_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<?> newCar(@RequestBody String payload) {
        int added = 0;
        int updatedID = 0;
        Cars rs;

        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(payload);
            JSONObject jsonObject = (JSONObject) object;
            // If 'id' in JSON payload is not null, get me the true int value of 'id' or set it to 0
            int i = jsonObject.get("id") != null ? Double.valueOf(jsonObject.get("id").toString()).intValue() : 0;
            String mark = (String) jsonObject.get(CARS_MARK);
            String colour = (String) jsonObject.get(CARS_COLOUR);
            String yearOfProduction = (String) jsonObject.get(CARS_YEAR_OF_PRODUCTION);
            String peopleCapacity = (String) jsonObject.get(CARS_PEOPLE_CAPACITY);
            String numberOfDoors = (String) jsonObject.get(CARS_NUMBER_OF_DOORS);
            String gearbox = (String) jsonObject.get(CARS_GEARBOX);
            Boolean rented = (Boolean) jsonObject.get(CARS_RENTED);

            // If 'id' is greater than zero, we are updating an existing car
            if (i > 0) {
                updatedID = i;
                carRepo.updateCar(i, mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented);
            // If 'id' is 0 we are adding a new car
            } else {
                added = carRepo.addCar(mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented);
            }
        } catch (ParseException e) {
            return new ResponseEntity<String>("Check data.",HttpStatus.BAD_REQUEST);
        }
        try {
            if (added == 0) {
                rs = carRepo.getCar(updatedID);
            } else {
                rs = carRepo.lastCar();
            }
        }catch (ProductNotfoundException e){
            return new ResponseEntity<String>("Check data.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(rs, HttpStatus.OK);
    }

    @GetMapping(CARS_GET_CAR_PATH)
    public ResponseEntity<?> getCarById(@PathVariable(value = "id") int carId) {
        try {
            return new ResponseEntity(carRepo.getCar(carId), HttpStatus.OK);
        } catch (ProductNotfoundException exception){
            return new ResponseEntity<String>("Car not Found. Check data.",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(CARS_DELETE_CAR_PATH)
    @ResponseBody
    public List<Cars> deleteItem(@RequestParam("id") int itemId) {

        try {
            if (carRepo.deleteItem(itemId) >= 1) {
                return carRepo.getAllCars();
            }
        } catch (Exception e) {
            return carRepo.getAllCars();
        }

        return carRepo.getAllCars();
    }

    @PostMapping(CARS_RENT_CAR_PATH)
    @ResponseBody
    public ResponseEntity<?> rentCar(@RequestParam("id") int itemId) {
            try {
                carRepo.rentCar(itemId);
                return new ResponseEntity(carRepo.getCar(itemId), HttpStatus.OK);
            } catch (ProductRentedException e){
                return new ResponseEntity<String>("That cars is rented. Please chose other.",HttpStatus.BAD_REQUEST);
            }
    }

    @PostMapping(CARS_RETURN_CAR_PATH)
    @ResponseBody
    public ResponseEntity<?> returnCar(@RequestParam("id") int itemId) {
        try {
            carRepo.returnCar(itemId);
            return new ResponseEntity(carRepo.getCar(itemId), HttpStatus.OK);
        } catch (ProductNotRentException exception){
            return new ResponseEntity<String>("That cars isn't rented. Check data.",HttpStatus.BAD_REQUEST);
        }
    }



}