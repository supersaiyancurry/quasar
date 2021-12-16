package io.training.catalyte.sportsapparel.repositories;

import io.training.catalyte.sportsapparel.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  List<Customer> findByEmail(String email);

  boolean existsByEmail(String email);
}


