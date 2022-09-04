package com.init.usermicroservice.feingClients;


import com.init.usermicroservice.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bike-service" ,url = "http://localhost:8003",path = "/bikes")
//@RequestMapping("/bikes")
public interface BikeFeingClient {

    @PostMapping
    Bike save(@RequestBody Bike bike);

    @GetMapping("/byuser/{userId}")
    List<Bike> getBikes (@PathVariable Long userId);
}
