package com.anonimos.springboot.app.rents.service;

import com.anonimos.springboot.app.rents.model.entity.Rent;
import com.anonimos.springboot.app.rents.resource.Car;
import com.anonimos.springboot.app.rents.resource.Lessee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface RentService {
     List<Rent> getAll();
     Optional <Rent> findById(Long id);
     Optional<Rent> getById(Long id);
     Rent createRent(Rent rent);
     void DeleteRent(Long id);

     /**Microservices-iteration*/
     Optional<Rent> findByIdWithCars(Long id);
     Optional<Car> assignCar(Car car, Long lessorId);
     Optional<Car> unAssignCar(Car car, Long lessorId);

     /**Microservices-iteration*/




}
