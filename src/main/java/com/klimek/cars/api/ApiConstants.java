package com.klimek.cars.api;

import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
@NoArgsConstructor
public final class ApiConstants {
    public static final String API_PATH = "/api";
    public static final String CARS_GET_ALL_PATH = "/cars";
    public static final String CARS_GET_CAR_PATH = "/car/{id}";
    public static final String CARS_DELETE_CAR_PATH = "/cars/delete";
    public static final String CARS_RENT_CAR_PATH = "/cars/rent";
    public static final String CARS_RETURN_CAR_PATH = "/cars/return";
}
