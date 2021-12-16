package io.training.catalyte.sportsapparel.services;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.training.catalyte.sportsapparel.entities.Customer;
import io.training.catalyte.sportsapparel.entities.LoginForm;
import io.training.catalyte.sportsapparel.entities.Product;
import io.training.catalyte.sportsapparel.exceptions.BadRequest;
import io.training.catalyte.sportsapparel.exceptions.DataAlreadyExists;
import io.training.catalyte.sportsapparel.exceptions.DataNotFound;
import io.training.catalyte.sportsapparel.exceptions.ServerError;
import io.training.catalyte.sportsapparel.exceptions.ServiceUnavailable;
import io.training.catalyte.sportsapparel.repositories.CustomerRepository;
import io.training.catalyte.sportsapparel.secrets.Secret;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.client.HttpClientErrorException;
/**
 * The Customer Service Implementation Class implements the methods from the Customer Service
 * Interface.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final ProductService productService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ProductService productService) {
        this.customerRepository = customerRepository;
        this.productService = productService;
    }
    /**
     * Finds all customers with the given parameter in the database and creates a list of them.
     *
     * @param customer list to be found. If the size of the list of customer in the customer
     *                 repository with the given parameter does not equal 0,
     * @return all customers in the database. If no customers are found, the Data Not Found exception
     * will be thrown. If there is a server error, the Server Error Exception will be thrown.
     */
    public List<Customer> findAll(Customer customer, String token) {
        try {
            List<Customer> customers = customerRepository.findAll(Example.of(customer));
            if (customers.isEmpty()) {
                throw new NotFoundException("");
            } else {
                return customers;
            }
        } catch (
                NotFoundException dnf) {
            throw new DataNotFound();
        } catch (
                CannotCreateTransactionException cte) {
            throw new ServerError();
        }
    }
    /**
     * finds a customer in the database with the provided email
     *
     * @param email - the email of the customer to find
     * @param token - the jwt token string
     * @return the customer found with the given email
     */
    public Customer findByEmail(String email, String token) {
        try {
            List<Customer> customers = Optional.of(customerRepository.findByEmail(email)).orElse(null);
            if (customers.size() != 0) {
                return customers.get(0);
            }
        } catch (DataAccessException dae) {
            throw new ServiceUnavailable(dae);
        }
        throw new DataNotFound();
    }
    /**
     * Posts a new customer into the database.
     *
     * @param customer customer to be added to database If the data is already there, the Data Already
     *                 Exists Exception will be thrown. If there is a server error, the Server
     *                 Exception will be thrown.
     */
    public Customer addCustomer(Customer customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
        try {
            customerRepository.save(customer);
            return customer;
        } catch (HttpClientErrorException.BadRequest br) {
            throw new BadRequest();
        } catch (DataIntegrityViolationException div) {
            throw new DataAlreadyExists();
        } catch (DataAlreadyExists dae) {
            throw dae;
        } catch (CannotCreateTransactionException se) {
            throw new ServerError();
        }
    }
    /**
     * Logs the customer in.
     *
     * @param loginForm whatever email and password the user puts in This function "login" finds the
     *                  corresponding customer in the database based on what email and password were
     *                  input into the login form. The customer retrieved is then used with the
     *                  generateToken function to create a token.
     * @return function call of "generateToken" for generating a token (basically, returns a token).
     * Obtaining the token completes the login process.
     * @throws DataNotFound
     * @throws BadRequest
     */
    public String login(LoginForm loginForm) throws DataNotFound, BadRequest {
        try {
            List<Customer> customers = customerRepository.findByEmail(loginForm.getEmail());
            if (customers.size() != 1) {
                throw new DataNotFound();
            }
            Customer customer = customers.get(0);
            if (encoder.matches(loginForm.getPassword(), customer.getPassword())) {
                return generateToken(customer);
            } else {
                throw new DataNotFound();
            }
        } catch (DataNotFound e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequest();
        }
    }
    public Set<Product> getWishList(Long customerId) {
        try {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer != null) {
                System.out.println(customer.getWishList());
                return productService.getProductsFromList(customer.getWishList());
            }
        } catch (DataAccessException dae) {
            // todo use Service Unavailable when user profile is merged
            throw new Error(dae);
        }
        throw new DataNotFound();
    }
    public Customer addToWishList(Product product, Long customerId) {
        try {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer != null) {
                customer.addToWishList(product.getId());
                return customerRepository.save(customer);
            }
        } catch (DataAccessException dae) {
            throw new ServiceUnavailable(dae);
        }
        throw new DataNotFound();
    }
    /**
     * takes a customer id and valid customer object and attempts to overwrite an existing customer
     * entry in the database
     *
     * @param customerId - the id of the customer to update
     * @param customer   - the new data of the customer
     * @return - the newly saved customer object
     */
    public Customer updateCustomer(Long customerId, Customer customer) {
        if (!customerId.equals(customer.getId())) {
            throw new BadRequest();
        }
        try {
            Customer customerInDb = customerRepository.findById(customerId).orElse(null);
            if (customerInDb != null) {
                // makes sure the email is unique if it is changed
                if (!customerInDb.getEmail().equals(customer.getEmail()) && customerRepository
                        .existsByEmail(customer.getEmail())) {
                    throw new DataAlreadyExists();
                }
                return customerRepository.save(customer);
            }
        } catch (DataAccessException dae) {
            throw new ServiceUnavailable(dae);
        }
        throw new DataNotFound();
    }
    /** Retrieves the customers shopping cart
     *
     * @param customerId - the id of the customer
     * @return the Set(List of Objects) in the customers shopping cart
     */
    @Override
    public Set<Product> getShoppingCart(Long customerId) {
        try {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer != null) {
                System.out.println(customer.getShoppingCart());
                return productService.getProductsFromList(customer.getShoppingCart());
            }
        } catch (DataAccessException dae) {
            throw new Error(dae);
        }
        throw new DataNotFound();
    }
    /** Adds a product to the shopping cart
     *
     * @param product - the product to add to the shopping cart
     * @param customerId - the id of the customer to add the product to
     * @return - save the product to the shopping cart
     */
    @Override
    public Customer addToShoppingCart(Product product, Long customerId) {
        try {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer != null) {
                customer.addToShoppingCart(product.getId());
                return customerRepository.save(customer);
            }
        } catch (DataAccessException dae) {
            throw new ServiceUnavailable(dae);
        }
        throw new DataNotFound();
    }
    /**
     * Generates a token.
     * <p>
     * Utilizes the added JSON web token dependency (Jwts.builder) to write a garbled string. The
     * garbled string is a result of information encoded using BASE64. The string is referred to as a
     * "token".
     *
     * @param customer because the encoding requires the customer's email.
     * @return jws, a token. If a user knows how to, they can use the "Secret" string to decode jws
     * for it's information.
     */
    public String generateToken(Customer customer) {
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + (100000 * 600));
        String jws = Jwts.builder()
                .setIssuer("Joel Yesupriya")
                .setSubject(customer.getEmail())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(Secret.getSECRET()))
                .compact();
        return jws;
    }
    @Override
    public Optional<Customer> findById(Long id, String token) {
        return Optional.empty();
    }
}