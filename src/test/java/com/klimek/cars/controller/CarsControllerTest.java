package com.klimek.cars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klimek.cars.model.Cars;
import com.klimek.cars.repository.CarsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarsRepository carsRepository;

    @Test
    public void Should_createCar_When_ValidRequest() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mark\":\"Fiat\",\n" +
                        "   \"colour\":\"Black\",\n" +
                        "   \"yearOfProduction\":\"2013\",\n" +
                        "   \"peopleCapacity\":\"2\",\n" +
                        "   \"gearbox\":\"automatic\",\n" +
                        "   \"rented\":false,\n" +
                        "   \"numberOfDoors\":\"3\"}")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(builder)
                .andExpect(jsonPath("$.mark").value("Fiat"))
                .andExpect(jsonPath("$.colour").value("Black"))
                .andExpect(jsonPath("$.yearOfProduction").value("2013"))
                .andExpect(jsonPath("$.peopleCapacity").value("2"))
                .andExpect(jsonPath("$.gearbox").value("automatic"))
                .andExpect(jsonPath("$.numberOfDoors").value("3"))
                .andExpect(jsonPath("$.rented").value("false"));
    }

    @Test
    public void Should_updateDataCar_When_ValidRequest() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"3\",\n" +
                        "   \"mark\":\"Seat\",\n" +
                        "   \"colour\":\"Pink\",\n" +
                        "   \"yearOfProduction\":\"2016\",\n" +
                        "   \"peopleCapacity\":\"5\",\n" +
                        "   \"gearbox\":\"automatic\",\n" +
                        "   \"rented\":false,\n" +
                        "   \"numberOfDoors\":\"3\"}")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(builder)
                .andExpect(jsonPath("$.mark").value("Seat"))
                .andExpect(jsonPath("$.colour").value("Pink"))
                .andExpect(jsonPath("$.yearOfProduction").value("2016"))
                .andExpect(jsonPath("$.peopleCapacity").value("5"))
                .andExpect(jsonPath("$.gearbox").value("automatic"))
                .andExpect(jsonPath("$.numberOfDoors").value("3"))
                .andExpect(jsonPath("$.rented").value("false"));
    }

    @Test
    public void Should_returnCar_When_ValidRequest() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/api/car/1");
        mockMvc.perform(builder)
                .andExpect(jsonPath("$.mark").value("Mercedes"))
                .andExpect(jsonPath("$.colour").value("Black"))
                .andExpect(jsonPath("$.yearOfProduction").value("1992"))
                .andExpect(jsonPath("$.peopleCapacity").value("4"))
                .andExpect(jsonPath("$.gearbox").value("manual"))
                .andExpect(jsonPath("$.numberOfDoors").value("5"))
                .andExpect(jsonPath("$.rented").value("false"));
    }

    @Test
    public void Should_deleteCar_When_ValidRequest() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/api/cars/delete").param("id", "4");
        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    public void Should_RentedValueToTrue_When_ValidRequest() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/api/cars/rent").param("id", "1");
        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    public void Should_RentedValueToFalse_When_ValidRequest() throws Exception {
        RequestBuilder builderRent = MockMvcRequestBuilders.post("/api/cars/rent").param("id", "1");
        mockMvc.perform(builderRent);

        RequestBuilder builderReturn = MockMvcRequestBuilders.post("/api/cars/return").param("id", "1");
        mockMvc.perform(builderReturn)
                .andExpect(status().isOk());
    }
}
