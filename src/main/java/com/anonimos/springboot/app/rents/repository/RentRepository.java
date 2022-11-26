package com.anonimos.springboot.app.rents.repository;

import com.anonimos.springboot.app.rents.model.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent,Long> {

    @Modifying
    @Query("DELETE FROM RentCar lc WHERE lc.carId=?1")
    void deleteRentCarByID(Long id);
}
