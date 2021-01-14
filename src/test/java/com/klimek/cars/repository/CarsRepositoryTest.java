package com.klimek.cars.repository;

import com.klimek.cars.DemoApplication;
import com.klimek.cars.model.Cars;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = "com.klimek.cars.")
public class CarsRepositoryTest extends TestCase {

    @Autowired
    private CarsRepository carsRepository;

    @Test
    public void when_getAllCars_expect_listOfCarsAsNonNullCarsList() {

        List<Cars> dbCar = carsRepository.getAllCars();
        Assert.assertNotNull(dbCar);
    }

    @Test
    public void when_getCarsToRent_expect_listOfCarsAsNonNullCarsList() {

        List<Cars> dbCar = carsRepository.getCarsToRent();
        Assert.assertNotNull(dbCar);
    }

    @Test
    public void when_getLastCar_expect_CarAsNonNullCars() {

        Cars dbCar = carsRepository.lastCar();
        Assert.assertNotNull(dbCar);
    }

    @Test
    public void when_validId_expect_getCarAsNonNullCar() {
        Cars car = new Cars();
        car.setMark("Porshe");
        car.setColour("red");
        car.setYearOfProduction("2020");
        car.setPeopleCapacity("2");
        car.setNumberOfDoors("2");
        car.setGearbox("manual");
        car.setRented(false);
        carsRepository.addCar(car);
        carsRepository.addCar("Fiat","Blue","2019","5","3","automatic",false);

        Cars dbCar = carsRepository.getCar(4);
        Assert.assertNotNull(dbCar);
        Assert.assertEquals("4",dbCar.getId().toString());
        Assert.assertEquals("Porshe",dbCar.getMark());
        Assert.assertEquals("red",dbCar.getColour());
        Assert.assertEquals("2020",dbCar.getYearOfProduction());
        Assert.assertEquals("2",dbCar.getPeopleCapacity());
        Assert.assertEquals("2",dbCar.getNumberOfDoors());
        Assert.assertEquals("manual",dbCar.getGearbox());
        Assert.assertEquals(false,dbCar.isRented());
    }

    @Test
    public void when_updateCarsDate_expect_CarWithNewData() {

        carsRepository.updateCar(6,"Fiat","Green","2018","3","2","manual",false);
        Cars dbCarInsertCase = carsRepository.lastCar();
        Assert.assertNotNull(dbCarInsertCase);
        Assert.assertEquals("6",dbCarInsertCase.getId().toString());
        Assert.assertEquals("Fiat",dbCarInsertCase.getMark());
        Assert.assertEquals("Green",dbCarInsertCase.getColour());
        Assert.assertEquals("2018",dbCarInsertCase.getYearOfProduction());
        Assert.assertEquals("3",dbCarInsertCase.getPeopleCapacity());
        Assert.assertEquals("2",dbCarInsertCase.getNumberOfDoors());
        Assert.assertEquals("manual",dbCarInsertCase.getGearbox());
        Assert.assertEquals(false,dbCarInsertCase.isRented());

        int updatedCarIdUpdateCase = carsRepository.updateCar(2,"Fiat","Withe","2017","2","2","manual",false);
        Cars dbCarUpdateCase = carsRepository.getCar(updatedCarIdUpdateCase);
        Assert.assertNotNull(dbCarUpdateCase);
        Assert.assertEquals("2",dbCarUpdateCase.getId().toString());
        Assert.assertEquals("Fiat",dbCarUpdateCase.getMark());
        Assert.assertEquals("Withe",dbCarUpdateCase.getColour());
        Assert.assertEquals("2017",dbCarUpdateCase.getYearOfProduction());
        Assert.assertEquals("2",dbCarUpdateCase.getPeopleCapacity());
        Assert.assertEquals("2",dbCarUpdateCase.getNumberOfDoors());
        Assert.assertEquals("manual",dbCarUpdateCase.getGearbox());
        Assert.assertEquals(false,dbCarUpdateCase.isRented());
    }

    @Test
    public void when_deleteCarDate_expect_CarAsEmptyCar() {
        carsRepository.deleteItem(6);

        Cars dbCarDeleteCase = carsRepository.getCar(6);
        Assert.assertNotNull(dbCarDeleteCase);
        Assert.assertEquals(null,dbCarDeleteCase.getId());
        Assert.assertEquals(null,dbCarDeleteCase.getMark());
        Assert.assertEquals(null,dbCarDeleteCase.getColour());
        Assert.assertEquals(null,dbCarDeleteCase.getYearOfProduction());
        Assert.assertEquals(null,dbCarDeleteCase.getPeopleCapacity());
        Assert.assertEquals(null,dbCarDeleteCase.getNumberOfDoors());
        Assert.assertEquals(null,dbCarDeleteCase.getGearbox());
        Assert.assertEquals(false,dbCarDeleteCase.isRented());
    }

    @Test
    public void when_rentCar_expect_CarRentedValueAsTrue() {
        carsRepository.rentCar(2);
        Cars dbCarRentCase = carsRepository.getCar(2);
        Assert.assertNotNull(dbCarRentCase);
        Assert.assertEquals(true,dbCarRentCase.isRented());
    }

    @Test
    public void when_returnCar_expect_CarRentedValueAsFalse() {
        carsRepository.rentCar(2);
        carsRepository.returnCar(2);
        Cars dbCarReturnCase = carsRepository.getCar(2);
        Assert.assertNotNull(dbCarReturnCase);
        Assert.assertEquals(false,dbCarReturnCase.isRented());
    }


}