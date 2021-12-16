package io.training.catalyte.sportsapparel.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.training.catalyte.sportsapparel.entities.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
  ResultMatcher badData = MockMvcResultMatchers.status().isBadRequest();
  ResultMatcher notFoundStatus = MockMvcResultMatchers.status().isNotFound();
  ResultMatcher createdStatus = MockMvcResultMatchers.status().isCreated();
  ResultMatcher conflictStatus = MockMvcResultMatchers.status().isConflict();
  ResultMatcher expectedType = MockMvcResultMatchers.content()
      .contentType(MediaType.APPLICATION_JSON_UTF8);

  @Before
  public void setUp() throws Exception {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();
  }

  @Test
  public void getCustomers() throws Exception {
    mockMvc
        .perform(get("/customers").header("token", "eyJhbGciOiJIUzI1NiJ9."
            + "eyJpc3MiOiJKYW1lcyBKb3dlcnMiLCJzdWIiOiJqb2VseWVzdXByaXlhQGdtYWlsLmNvbSIsImlhdCI6MTY"
            + "xMDQ4MjI3OCwiZXhwIjoxNjEwNTQyMjc4fQ.4TzgiZs4HmBHI3bnxcYM_udhQDZ6dNCu9anJQzjmfYE"))
        .andExpect(jsonPath("$[0].lastName", is("Doe")))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void getCustomerByEmail() throws Exception {
    mockMvc
        .perform(get("/customers?email=jdoe@gmail.com").header("token", "bananas"))
        .andExpect(jsonPath("$.firstName", is("John")))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void getCustomerByNonExistingEmail() throws Exception {
    mockMvc
        .perform(get("/customers?email=je@gmail.com").header("token", "bananas"))
        .andExpect(notFoundStatus);
  }

  @Test
  public void addCustomer() throws Exception {
    String json = "{\"firstName\":\"Joel\",\"lastName\":\"Yesupriya\",\"phone\":\"2403383610\","
        + "\"email\":\"joelyesupriya@gmail.com\",\"street\":\"1921 Forest Lane\","
        + "\"city\":\"Baltimore\",\"state\":\"MD\",\"zip\":\"20906\",\"password\":\"password\"}";

    mockMvc
        .perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(jsonPath("$.firstName", is("Joel")))
        .andExpect(createdStatus)
        .andExpect(expectedType);
  }

  @Test
  public void addCustomerBadEmail() throws Exception {
    String json = "{\"firstName\":\"Joel\",\"lastName\":\"Yesupriya\",\"phone\":\"2403383610\","
        + "\"email\":\"joelyesuprimail.com\",\"street\":\"1921 Forest Lane\","
        + "\"city\":\"Baltimore\",\"state\":\"MD\",\"zip\":\"20906\",\"password\":\"password\"}";

    mockMvc
        .perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(badData);
  }

  @Test
  public void getWishList() throws Exception {
    mockMvc
        .perform(get("/customers/1/wishlist"))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void getWishList2() throws Exception {
    mockMvc
        .perform(get("/customers/2/wishlist"))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void addToWishList() throws Exception {
    String json = "{\"id\":3411,\"price\":56.37,\"name\":\"Fashionable Running Headband\","
        + "\"description\":\"This running headband for men is extremely fashionable. "
        + "You're going to love it!\",\"demographic\":\"Men\",\"category\":\"Running\","
        + "\"type\":\"Headband\",\"releaseDate\":\"2017-01-01\",\"primaryColorCode\":\"#f9845b\","
        + "\"secondaryColorCode\":\"#39add1\",\"styleNumber\":\"sc31982\","
        + "\"globalProductCode\":\"po-8956920\"}";

    mockMvc
        .perform(put("/customers/2/wishlist")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(jsonPath("$.wishList[2]", is(3411)))
        .andExpect(createdStatus)
        .andExpect(expectedType);
  }

  @Test
  public void updateCustomerAddress() throws Exception {

    ObjectMapper mapper = new ObjectMapper();
    Customer customer = new Customer("John", "Doe", null,
        "jdoe@gmail.com", "1234 W Hickory St.", "Wichita", "KS", "12345", "Password", null);
    customer.setId(1L);
    String customerJSON = mapper.writeValueAsString(customer);
    mockMvc
        .perform(put("/customers/1/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerJSON))
        .andExpect(jsonPath("$.street", is("1234 W Hickory St.")))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void updateCustomerAddressInvalidId() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    Customer customer = new Customer("John", "Doe", null,
        "jdoe@gmail.com", "1234 W Hickory St.", "Wichita", "KS", "12345", "Password", null);
    customer.setId(100L);
    String customerJSON = mapper.writeValueAsString(customer);
    mockMvc
        .perform(put("/customers/100/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerJSON))
        .andExpect(notFoundStatus);
  }

  @Test
  public void updateCustomerAddressBadData() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    Customer customer = new Customer("John", "Doe", null,
        "jdoe@gmail.com", "1234 W Hickory St.", "Wichita", "KS", "12345", "Password", null);
    customer.setId(1L);
    String customerJSON = mapper.writeValueAsString(customer);
    mockMvc
        .perform(put("/customers/100/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerJSON))
        .andExpect(badData);
  }

  @Test
  public void updateCustomerEmailConflict() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    Customer customer = new Customer("John", "Doe", null,
        "janed@gmail.com", null, null, null, null, "Password", null);
    customer.setId(1L);
    String customerJSON = mapper.writeValueAsString(customer);
    mockMvc
        .perform(put("/customers/1/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(customerJSON))
        .andExpect(conflictStatus);
  }

  @Test
  public void getShoppingCart() throws Exception {
    mockMvc
        .perform(get("/customers/1/cart"))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void addToShoppingCart() throws Exception {
    String json = "{\"id\":3411,\"price\":56.37,\"name\":\"Fashionable Running Headband\","
        + "\"description\":\"This running headband for men is extremely fashionable. "
        + "You're going to love it!\",\"demographic\":\"Men\",\"category\":\"Running\","
        + "\"type\":\"Headband\",\"releaseDate\":\"2017-01-01\",\"primaryColorCode\":\"#f9845b\","
        + "\"secondaryColorCode\":\"#39add1\",\"styleNumber\":\"sc31982\","
        + "\"globalProductCode\":\"po-8956920\"}";

    mockMvc
        .perform(put("/customers/2/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(jsonPath("$.shoppingCart[0]", is(3411)))
        .andExpect(createdStatus)
        .andExpect(expectedType);
  }
}