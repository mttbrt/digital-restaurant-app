package com.mttbrt.digres.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mttbrt.digres.DigitalRestaurantApp;
import com.mttbrt.digres.domain.Authority;
import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.repository.AuthorityRepository;
import com.mttbrt.digres.repository.UserRepository;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DigitalRestaurantApp.class)
public class AuthControllerIT {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthorityRepository authorityRepository;

  @Test
  public void test() {
    Authority authority = authorityRepository.findByName("ROLE_STAFF");
    User savedUser = userRepository.save(new User("test", "test", Set.of(authority)));
    User foundUser = userRepository.findByUsername(savedUser.getUsername());

    assertNotNull(foundUser);
    assertEquals(savedUser.getPassword(), foundUser.getPassword());
  }
}