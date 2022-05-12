package com.digital.repository;

import com.digital.domain.entity.Authority;
import com.digital.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

  Authority findByName(String name);

}