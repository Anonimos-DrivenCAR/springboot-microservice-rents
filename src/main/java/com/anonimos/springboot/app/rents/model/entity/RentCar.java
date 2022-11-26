package com.anonimos.springboot.app.rents.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class RentCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name="car_id", unique = true)
    private Long carId;

    @Override
    public boolean equals(Object obj) {
        if(this  == obj)
            return true;

        if(!(obj instanceof RentCar))
            return false;

        RentCar o = (RentCar) obj;
        return this.carId != null && this.carId.equals(o.carId);
    }
}
