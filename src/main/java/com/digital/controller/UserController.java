package com.digital.controller;

import com.digital.model.entity.User;
import com.digital.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

  private UserRepository userRepository;

  @Autowired
  public void setEmployeeController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/home")
  public String getHome() {
    return "Home";
  }

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/admin")
  public String getAdminPanel() {
    return "Admin panel";
  }

}
