package com.mttbrt.digres.repository;

import com.mttbrt.digres.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityDao extends JpaRepository<Authority, Long> {

  Authority findByName(String name);

}