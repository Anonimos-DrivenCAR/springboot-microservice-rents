package com.anonimos.springboot.app.rents.model.entity;

import com.anonimos.springboot.app.rents.resource.Car;
import com.anonimos.springboot.app.rents.shared.domain.model.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rents")
public class Rent extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rentNo;
    private BigDecimal cost;
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RentItem> rentItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id")
    private List<RentCar> rentCars = new ArrayList<>();

    @Transient
    private List<Car> cars = new ArrayList<>();

    public void addRentCar(RentCar rentCar){
        rentCars.add(rentCar);
    }
    public void removeRentCar(RentCar rentCar){
        rentCars.remove(rentCar);
    }
}
