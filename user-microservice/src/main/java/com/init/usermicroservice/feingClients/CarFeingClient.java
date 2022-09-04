package com.init.usermicroservice.feingClients;

import com.init.usermicroservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service" ,url = "http://localhost:8002",path = "/cars")
//@RequestMapping("/cars")
public interface CarFeingClient {
    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/byuser/{userId}")
    List<Car> getCars (@PathVariable Long userId);

}
