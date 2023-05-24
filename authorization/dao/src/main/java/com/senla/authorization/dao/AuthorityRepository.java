package com.senla.authorization.dao;

import com.senla.authorization.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
