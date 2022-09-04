package com.init.bikemicroservice.controller;


import com.init.bikemicroservice.entity.Bike;
import com.init.bikemicroservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> gelAll(){
        List<Bike> bikes = bikeService.getAll();
        if (bikes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getById(@PathVariable Long id){
        Bike bike = bikeService.bikeById(id);
        if (bike == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity<Bike> saveBike(@RequestBody Bike bike){
        Bike bikeNew = bikeService.save(bike);
        if (bikeNew == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bikeNew);
    }


    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<Bike>> gelByUserId(@PathVariable Long id){

        List<Bike> bikes = bikeService.buscarPorUserId(id);

        return ResponseEntity.ok(bikes);
    }



}
