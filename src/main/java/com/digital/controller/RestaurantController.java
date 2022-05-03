package com.digital.controller;

import com.digital.model.Restaurant;
import com.digital.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public void setEmployeeController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

}
