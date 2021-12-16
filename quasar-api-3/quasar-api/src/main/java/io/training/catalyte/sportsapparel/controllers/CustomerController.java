package io.training.catalyte.sportsapparel.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.training.catalyte.sportsapparel.entities.Customer;
import io.training.catalyte.sportsapparel.entities.LoginForm;
import io.training.catalyte.sportsapparel.entities.Product;
import io.training.catalyte.sportsapparel.exceptions.BadRequest;
import io.training.catalyte.sportsapparel.exceptions.DataNotFound;
import io.training.catalyte.sportsapparel.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * GET: Calls the service to retrieve ALL customers in the database. Requires a token in the
     * header (obtained from logging in) in order to retrieve the information.
     *
     * @param customer: the list of customers to return.
     * @return a list of all customers plus a specified response code.
     * @header token: the required token from logging in.
     */
    @ApiOperation("Gets all customers in the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful get.", responseContainer = "List", response = Customer.class)
    })
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(Customer customer,
                                                       @RequestHeader(value = "token") String token) {
        return new ResponseEntity(customerService.findAll(customer, token), HttpStatus.OK);
    }

  /**
   * GET: Calls the service to retrieve the customer with the given email header (obtained from
   * logging in) in order to retrieve the information.
   *
   * @param email: the email of the customer to return.
   * @return a list of all customers plus a specified response code.
   * @header token: the required token from logging in.
   */
  @ApiOperation("Gets a customer in the system that matches to given email.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful get.", response = Customer.class),
      @ApiResponse(code = 404, message = "No customer with that email.", response = DataNotFound.class)
  })
  @GetMapping(params = {"email"})
  public ResponseEntity<List<Customer>> getCustomerByEmail(@RequestParam("email") String email,
      @RequestHeader(value = "token") String token) {
    Customer customer = customerService.findByEmail(email, token);
    return new ResponseEntity(customer, HttpStatus.OK);
  }

    /**
     * POST: Calls the service to add a new customer to the database.
     *
     * @param customer: the customer to save.
     * @return customer that was saved plus a specified response code.
     */
    @ApiOperation("Add a customer to the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful post.", response = Customer.class),
            @ApiResponse(code = 400, message = "Bad information in your post."),
            @ApiResponse(code = 409, message = "Email is already in the database. Cannot be re-used.")
    })
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
        Customer newCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    /**
     * PUT: Calls the service to add a new item to a customer's wishlist
     *
     * @param product    - the product to add to the list
     * @param customerId - the customer's id
     * @return the newly updated customer
     */
    @ApiOperation("Adds a product to a customer's wishlist.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Wishlist updated.", response = Customer.class),
            @ApiResponse(code = 400, message = "Bad information in your post."),
            @ApiResponse(code = 404, message = "Could not find a customer with that id")
    })
    @PutMapping(value = "/{customer-id}/wishlist")
    public ResponseEntity<Customer> addToWishList(@Valid @RequestBody Product product,
                                                  @PathVariable(value = "customer-id") Long customerId) {
        return new ResponseEntity(customerService.addToWishList(product, customerId),
                HttpStatus.CREATED);
    }

    /**
     * GET: Calls the service to retrieve a customer's wishlist
     *
     * @param customerId - the id of the customer
     * @return
     */
    @GetMapping(value = "/{customer-id}/wishlist")
    public ResponseEntity<List<Product>> getCustomerWishList(
            @PathVariable(value = "customer-id") Long customerId) {
        return new ResponseEntity(customerService.getWishList(customerId), HttpStatus.OK);
    }

  /**
   * PUT: Calls the service to update an existing customer's info
   *
   * @param customerId - the id of the customer to update
   * @param customer   - the new data to replace the current customer's info with
   * @return a Response Entity containing the newly saved customer
   */
  @ApiOperation("Updates an existing customer.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Customer updated.", response = Customer.class),
      @ApiResponse(code = 400, message = "Bad information in your post."),
      @ApiResponse(code = 404, message = "Could not find a customer with that id"),
      @ApiResponse(code = 409, message = "That email is already taken")
  })
  @PutMapping(value = "/{customer-id}")
  public ResponseEntity<Customer> updateCustomer(
      @PathVariable(value = "customer-id") Long customerId, @RequestBody @Valid Customer customer) {
    return new ResponseEntity<>(customerService.updateCustomer(customerId, customer),
        HttpStatus.OK);
  }

  /**
   * POST: Calls the service to log the customer into the website.
   *
   * @param loginForm: the email and password being input.
   * @return function call of the service impl version of login.
   */
  @PostMapping(value = "/login")
  public String login(@Valid @RequestBody LoginForm loginForm)
      throws BadRequest, DataNotFound {
    return customerService.login(loginForm);
  }

  /**
   * PUT: Calls the service to add a new item to a customer's wishlist
   *
   * @param product    - the product to add to the list
   * @param customerId - the customer's id
   * @return the newly updated customer
   */
  @ApiOperation("Adds a product to a customer's shopping cart.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Wishlist updated.", response = Customer.class),
      @ApiResponse(code = 400, message = "Bad information in your post."),
      @ApiResponse(code = 404, message = "Could not find a customer with that id")
  })
  @PutMapping(value = "/{customer-id}/cart")
  public ResponseEntity<Customer> addToShoppingCart(@Valid @RequestBody Product product,
      @PathVariable(value = "customer-id") Long customerId) {
    return new ResponseEntity(customerService.addToShoppingCart(product, customerId),
        HttpStatus.CREATED);
  }

  /**
   * GET: Calls the service to retrieve a customer's wishlist
   *
   * @param customerId - the id of the customer
   * @return
   */
  @GetMapping(value = "/{customer-id}/cart")
  public ResponseEntity<List<Product>> getCustomerShoppingCart(
      @PathVariable(value = "customer-id") Long customerId) {
    return new ResponseEntity(customerService.getShoppingCart(customerId), HttpStatus.OK);
  }
}


