package com.init.usermicroservice.controller;

import com.init.usermicroservice.entity.User;
import com.init.usermicroservice.model.Bike;
import com.init.usermicroservice.model.Car;
import com.init.usermicroservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> gelAll(){
        List<User> users = userService.getAll();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> gelById(@PathVariable Long id){
        User user = userService.userById(id);
        if (user == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User userNew = userService.save(user);
        if (userNew == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(user);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") Long userId ){
        User user = userService.userById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();

        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Long userId, @RequestBody Car car){
        Car carNew = userService.saveCar(userId,car);
        return ResponseEntity.ok(carNew);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") Long userId ){
        User user = userService.userById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();

        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }


    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable Long userId, @RequestBody Bike bike){
        Bike bikeNew = userService.saveBike(userId,bike);
        return ResponseEntity.ok(bikeNew);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable Long userId){
        Map<String,Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }


    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") Long userId,RuntimeException e ){
        return new ResponseEntity("microservicio de carros caido, getCars", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(@PathVariable Long userId, @RequestBody Car car,RuntimeException e ) {
        return new ResponseEntity("microservicio de carros caido, saveCar", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") Long userId,RuntimeException e  ){
        return new ResponseEntity("microservicio de motos caido, getBikes", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallBackSaveBike(@PathVariable Long userId, @RequestBody Bike bike,RuntimeException e ){
        return new ResponseEntity("microservicio de motos caido, saveBike", HttpStatus.OK);
    }



    private ResponseEntity<Map<String,Object>> fallBackGetAll(@PathVariable Long userId,RuntimeException e ){
        return new ResponseEntity("microservicio de carros o motos caido, getAll", HttpStatus.OK);
    }



}
