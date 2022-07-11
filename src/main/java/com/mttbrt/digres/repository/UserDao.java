package com.mttbrt.digres.repository;

import com.mttbrt.digres.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

}