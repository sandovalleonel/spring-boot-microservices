package com.init.carmicroservice.controller;

import com.init.carmicroservice.entity.Car;
import com.init.carmicroservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> gelAll(){
        List<Car> cars = carService.getAll();
        if (cars.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable Long id){
        Car car = carService.carById(id);
        if (car == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> saveCar(@RequestBody Car car){
        Car carNew = carService.save(car);
        if (carNew == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(car);
    }


    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<Car>> gelByUserId(@PathVariable Long id){
        System.out.println(id);
        List<Car> cars = carService.buscarPorUserId(id);
        if (cars.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cars);
    }



}
