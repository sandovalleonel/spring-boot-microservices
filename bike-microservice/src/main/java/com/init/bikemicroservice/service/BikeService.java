package com.init.bikemicroservice.service;


import com.init.bikemicroservice.entity.Bike;
import com.init.bikemicroservice.repository.BikeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    @Autowired
    private BikeRepository bikeRepository;

    public List<Bike> getAll(){
        return bikeRepository.findAll();
    }

    public Bike bikeById(Long id){
        return bikeRepository.findById(id).orElse(null);
    }

    public Bike save(Bike bike){
        return bikeRepository.save(bike);
    }

    public List<Bike> buscarPorUserId(Long id){
        return bikeRepository.findByUserId(id);

    }
}
