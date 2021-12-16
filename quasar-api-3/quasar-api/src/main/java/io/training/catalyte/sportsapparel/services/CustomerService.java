package io.training.catalyte.sportsapparel.services;

import io.training.catalyte.sportsapparel.entities.Customer;
import io.training.catalyte.sportsapparel.entities.LoginForm;
import io.training.catalyte.sportsapparel.entities.Product;
import io.training.catalyte.sportsapparel.exceptions.BadRequest;
import io.training.catalyte.sportsapparel.exceptions.DataNotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface CustomerService {

    List<Customer> findAll(Customer customer, String token);

    Customer findByEmail(String email, String token);

    Customer addCustomer(Customer customer);

    Optional<Customer> findById(Long id, String token);

    String login(LoginForm loginForm)
            throws DataNotFound, BadRequest
            ;

    Set<Product> getWishList(Long customerId);

    Customer addToWishList(Product product, Long customerId);

  Customer updateCustomer(Long customerId, Customer customer);

  Set<Product> getShoppingCart(Long customerId);

  Customer addToShoppingCart(Product product, Long customerId);
}
