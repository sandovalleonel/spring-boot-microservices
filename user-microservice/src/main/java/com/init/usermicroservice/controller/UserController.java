package com.init.usermicroservice.controller;

import com.init.usermicroservice.entity.User;
import com.init.usermicroservice.model.Bike;
import com.init.usermicroservice.model.Car;
import com.init.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") Long userId ){
        User user = userService.userById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();

        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") Long userId ){
        User user = userService.userById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();

        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }
}
