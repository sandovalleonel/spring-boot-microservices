package com.init.usermicroservice.service;

import com.init.usermicroservice.entity.User;
import com.init.usermicroservice.model.Bike;
import com.init.usermicroservice.model.Car;
import com.init.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAll(){

        return userRepository.findAll();
    }

    public User userById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public List<Car> getCars(Long userId){
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/cars/byuser/"+userId, List.class);
        return cars;
    }

    public List<Bike> getBikes(Long userId){
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bikes/byuser/"+userId, List.class);
        return bikes;
    }
}
