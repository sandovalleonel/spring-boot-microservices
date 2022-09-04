package com.init.usermicroservice.service;

import com.init.usermicroservice.entity.User;
import com.init.usermicroservice.feingClients.BikeFeingClient;
import com.init.usermicroservice.feingClients.CarFeingClient;
import com.init.usermicroservice.model.Bike;
import com.init.usermicroservice.model.Car;
import com.init.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeingClient carFeingClient;

    @Autowired
    private BikeFeingClient bikeFeingClient;

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

    public Car saveCar(Long userId,Car car){
        car.setUserId(userId);
        Car carNew = carFeingClient.save(car);
        return carNew;
    }


    public Bike saveBike(Long userId,Bike bike){
        bike.setUserId(userId);
        Bike bikeNew = bikeFeingClient.save(bike);
        return bikeNew;
    }


    public Map<String,Object> getUserAndVehicles(Long id){
        Map<String,Object> result = new HashMap<>();
        User user = userRepository.findById(id).orElse(null);

        if (user == null){
            result.put("mensaje","No existe el usuario");
            return result;
        }
        result.put("User",user);
        List<Car> cars = carFeingClient.getCars(user.getId());
        if (cars.isEmpty())
            result.put("Cars","Usuario no tiene carros");
        else
            result.put("Cars",cars);

        List<Bike> bikes = bikeFeingClient.getBikes(user.getId());
        if (bikes.isEmpty())
            result.put("Bikes","Usuario no tiene bikes");
        else
            result.put("Bikes",bikes);



        return result;

    }
}
