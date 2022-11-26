package com.anonimos.springboot.app.rents.client;
import com.anonimos.springboot.app.rents.model.entity.Rent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-stocks")
public interface StockClient {
    @RequestMapping(" /get-by-code/{code}")
    boolean stockAvailable(@PathVariable String code);
    @GetMapping("/{id}")
    Rent getById(@PathVariable Long id);

}
