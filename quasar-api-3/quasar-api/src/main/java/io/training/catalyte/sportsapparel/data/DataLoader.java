package io.training.catalyte.sportsapparel.data;

import io.training.catalyte.sportsapparel.entities.Customer;
import io.training.catalyte.sportsapparel.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    private final Customer customer1 = new Customer("John", "Doe", null,
            "jdoe@gmail.com", null, null, null, null, "Password",
            null);

    private final Customer customer2 = new Customer("Jane", "Doe", "111-111-1111",
            "janed@gmail.com", "123 Apple st", "City", "AK", "11111",
            "Pa$$word", null);

    private final Set<Long> wishlist1 = new HashSet<>();
    private final Set<Long> wishlist2 = new HashSet<>();

  private final Set<Long> shoppingCart = new HashSet<>();

  @Autowired
  private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        loadCustomers();

    }

  private void loadCustomers() {
    wishlist1.add(1L);
    customer1.setId(1L);
    customer1.setWishList(wishlist1);
    customer1.setPassword(passwordEncoder.encode("Password"));
    shoppingCart.add(1L);
    shoppingCart.add(15L);
    customer1.setShoppingCart(shoppingCart);
    customerRepository.save(customer1);

        wishlist2.add(2L);
        wishlist2.add(1L);
        customer2.setWishList(wishlist2);
        customer2.setId(2L);
        customer2.setPassword(passwordEncoder.encode("Pa$$word"));
        customerRepository.save(customer2);

    }
}
