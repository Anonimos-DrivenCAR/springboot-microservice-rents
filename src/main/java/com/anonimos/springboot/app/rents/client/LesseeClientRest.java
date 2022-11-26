package com.anonimos.springboot.app.rents.client;

import com.anonimos.springboot.app.rents.resource.Lessee;
import com.anonimos.springboot.app.rents.resource.Lessor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-lessees", url = "ec2-107-23-254-87.compute-1.amazonaws.com:8005/api/lessee")
public interface LesseeClientRest {
    @GetMapping("/{id}")
    Lessee getById(@PathVariable Long id);
    @GetMapping(value="/rents-by-lessor")
    List<Lessee> getRentsByLessor(@RequestParam Iterable<Long> ids);
}
