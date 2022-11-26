package com.anonimos.springboot.app.rents.service;

import com.anonimos.springboot.app.rents.client.CarClientRest;
import com.anonimos.springboot.app.rents.client.LesseeClientRest;
import com.anonimos.springboot.app.rents.model.entity.Rent;
import com.anonimos.springboot.app.rents.model.entity.RentCar;
import com.anonimos.springboot.app.rents.model.entity.RentLessee;
import com.anonimos.springboot.app.rents.repository.RentRepository;
import com.anonimos.springboot.app.rents.resource.Car;
import com.anonimos.springboot.app.rents.resource.Lessee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentServiceImpl implements RentService{

    private final RentRepository rentRepository;
    private final CarClientRest clientRest;
    private final LesseeClientRest lesseeClientRest;

    public RentServiceImpl(RentRepository rentRepository, CarClientRest clientRest,LesseeClientRest lesseeClientRest) {
        this.rentRepository = rentRepository;
        this.clientRest = clientRest;
        this.lesseeClientRest = lesseeClientRest;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rent> getAll() {
        return rentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rent> findById(Long id) {
        return Optional.ofNullable(rentRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rent> getById(Long id) {
        return rentRepository.findById(id);
    }

    @Override
    @Transactional
    public Rent createRent(Rent rent) {
        return rentRepository.save(rent);
    }

    @Override
    @Transactional
    public void DeleteRent(Long id) {
        rentRepository.deleteById(id);
    }



    /**Microservices-iterations*/
    @Override
    @Transactional(readOnly = true)
    public Optional<Rent> findByIdWithCars(Long id) {
        Optional<Rent> o = rentRepository.findById(id);
        if(o.isPresent()){
            Rent rent = o.get();
            if(!rent.getRentCars().isEmpty()){
                List<Long> ids = rent.getRentCars().stream().map(
                        RentCar::getCarId).collect(Collectors.toList());
                List<Car> cars = clientRest.getCarsByRent(ids);
                rent.setCars(cars);
            }
            return Optional.of(rent);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Car> assignCar(Car car, Long lessorId) {

        Optional<Rent> o = rentRepository.findById(lessorId);

        if(o.isPresent()){
            Car carMsvc = clientRest.getById(car.getIdCar());
            Rent rent= o.get();
            RentCar rentCar = new RentCar();
            rentCar.setCarId(carMsvc.getIdCar());

            rent.addRentCar(rentCar);
            rentRepository.save(rent);

            return Optional.of(carMsvc);
        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public Optional<Car> unAssignCar(Car car, Long lessorId) {
        Optional<Rent> o = rentRepository.findById(lessorId);
        if(o.isPresent()){
            Car carMsvc = clientRest.getById(car.getIdCar());

            Rent rent= o.get();
            RentCar lessorCar = new RentCar();
            lessorCar.setCarId(carMsvc.getIdCar());

            rent.removeRentCar(lessorCar);
            rentRepository.save(rent);

            return Optional.of(carMsvc);
        }
        return Optional.empty();
    }


}
