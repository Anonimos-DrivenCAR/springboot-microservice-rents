package com.anonimos.springboot.app.rents.resource;

import com.anonimos.springboot.app.rents.enums.CarType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {
    private Long idCar;
    private String brand;
    private CarType carType;
    private String color;
    private String model;
    private int productionYear;
    private int engineSize;
    private int power;
    private int mileage;
}
