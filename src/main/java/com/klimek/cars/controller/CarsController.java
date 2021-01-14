package com.klimek.cars.controller;

import java.util.List;

import com.klimek.cars.model.Cars;
import com.klimek.cars.repository.CarsRepository;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarsController {

    @Autowired
    CarsRepository carRepo;

    @GetMapping("/api/cars")
    @ResponseBody
    public List<Cars> getAll() {
            return carRepo.getAllCars();
    }

    @PostMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Cars newCar(@RequestBody String payload) {
        int added = 0;
        int updatedID = 0;
        Cars rs;

        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(payload);
            JSONObject jsonObject = (JSONObject) object;
            // If 'id' in JSON payload is not null, get me the true int value of 'id' or set it to 0
            int i = jsonObject.get("id") != null ? Double.valueOf(jsonObject.get("id").toString()).intValue() : 0;
            String mark = (String) jsonObject.get("mark");
            String colour = (String) jsonObject.get("colour");
            String yearOfProduction = (String) jsonObject.get("yearOfProduction");
            String peopleCapacity = (String) jsonObject.get("peopleCapacity");
            String numberOfDoors = (String) jsonObject.get("numberOfDoors");
            String gearbox = (String) jsonObject.get("gearbox");
            Boolean rented = (Boolean) jsonObject.get("rented");

            // If 'id' is greater than zero, we are updating an existing car
            if (i > 0) {
                updatedID = i;
                carRepo.updateCar(i, mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented);
            // If 'id' is 0 we are adding a new car
            } else {
                added = carRepo.addCar(mark, colour, yearOfProduction, peopleCapacity, numberOfDoors, gearbox, rented);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new Cars(null, "The car id " + updatedID + " does not exist.");
        }
        if (added == 0) {
            System.out.println("added:" + added);
            System.out.println("id:" + updatedID);
            rs = carRepo.getCar(updatedID);
        } else {
            rs = carRepo.lastCar();
        }
        return new Cars(rs.getId(), rs.getMark(), rs.getColour(), rs.getYearOfProduction(), rs.getPeopleCapacity(), rs.getNumberOfDoors(),rs.getGearbox(),rs.isRented());

    }

    @GetMapping("/api/car/{id}")
    public Cars getCarById(@PathVariable(value = "id") int carId) {
        try {
            return carRepo.getCar(carId);
        } catch (Exception e) {
            return new Cars(null, "The note id " + carId + " does not exist.");
        }

    }

    @PostMapping("/api/cars/delete")
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

    @PostMapping("/api/cars/rent")
    @ResponseBody
    public ResponseEntity<?> rentCar(@RequestParam("id") int itemId) {
            try {
                carRepo.rentCar(itemId);
                return new ResponseEntity(carRepo.getCar(itemId), HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity<String>("That cars is rented. Please chose other.",HttpStatus.BAD_REQUEST);
            }
    }

    @PostMapping("/api/cars/return")
    @ResponseBody
    public ResponseEntity<?> returnCar(@RequestParam("id") int itemId) {
        try {
            carRepo.returnCar(itemId);
            return new ResponseEntity(carRepo.getCar(itemId), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("That cars isn't rented. Check data.",HttpStatus.BAD_REQUEST);
        }
    }



}