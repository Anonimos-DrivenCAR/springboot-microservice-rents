package com.anonimos.springboot.app.rents.controllers;


import com.anonimos.springboot.app.rents.client.StockClient;
import com.anonimos.springboot.app.rents.model.entity.Rent;
import com.anonimos.springboot.app.rents.resource.Car;
import com.anonimos.springboot.app.rents.service.RentService;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/rent")
public class RentController {
    private  final RentService service;

    public RentController(RentService service,StockClient stockClient){
        this.service = service;
    }

    @GetMapping("/all")
    public Map<String,List<Rent>> getAll() {
        return Collections.singletonMap("rents",service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long  id){
        Optional<Rent> optionalRent = service.findById(id);
        if(optionalRent.isPresent()){
            return ResponseEntity.ok(optionalRent.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Rent rent, BindingResult result){
        if(result.hasErrors()){
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRent(rent));
    }
    @GetMapping("/details/{id}") //getByID -> Details
    public ResponseEntity<?> detail(@PathVariable Long id){
        Optional<Rent> o = service.findByIdWithCars(id);     //service.findLessorById(id);
        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable Long id) {
        Optional<Rent> l = service.getById(id);
        if(l.isPresent()){
            service.DeleteRent(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }


    /**Microservices Iteration*/
    //Lessor
    @PutMapping( "/assign-car/{lessorId}")
    public ResponseEntity<?> assignCar(@RequestBody Car car, @PathVariable Long lessorId){
        Optional<Car> o ;
        try {
            o = service.assignCar(car,lessorId);
        }catch (FeignException exception ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message","Does not exist car by id or communication error: " +
                    exception.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/unAssign-car/{rentId}")
    public ResponseEntity<?> deleteCar(@RequestBody Car car,  @PathVariable Long rentId){
        Optional<Car> o ;
        try {
            o = service.unAssignCar(car,rentId);
        }catch (FeignException exception ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message","Can't create the car or communication error : " +
                    exception.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }





    /**Validation*/
    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errors.put(err.getField(), "Field " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


}
