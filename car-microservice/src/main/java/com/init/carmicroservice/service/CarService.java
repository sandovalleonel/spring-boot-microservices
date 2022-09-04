package com.init.carmicroservice.service;


import com.init.carmicroservice.entity.Car;
import com.init.carmicroservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Car carById(Long id){
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car){
        return carRepository.save(car);
    }

    public List<Car> buscarPorUserId(Long id){
        return carRepository.findByUserId(id);

    }
}
