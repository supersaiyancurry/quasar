package io.training.catalyte.sportsapparel.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

import static io.training.catalyte.sportsapparel.constants.StringConstants.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(unique = true, columnDefinition = "serial")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_gen")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name="customers_gen", sequenceName="customers_seq")
  private Long id;

  @NotBlank(message = "First name" + REQUIRED_FIELD)
  private String firstName;

  @NotBlank(message = "Last name" + REQUIRED_FIELD)
  private String lastName;

  @Pattern(regexp = "^\\D?(\\d{3})\\D?\\D?(\\d{3})\\D?(\\d{4})$", message = INVALID_PHONE_NUMBER)
  private String phone;

  @Column(unique = true)
  @NotBlank(message = "Email" + REQUIRED_FIELD)
  @Email(message = INVALID_EMAIL)
  private String email;

  private String street;

  private String city;

  @Pattern(regexp =
      "^(AL|AK|AR|AZ|CA|CO|CT|DC|DE|FL|GA|HI|IA|ID|IL|IN|KS|KY|LA|MA|MD|ME|MI|MN|MO|MS|MT|NC|ND|NE"
          + "|NH|NJ|NM|NV|NY|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VA|VT|WA|WI|WV|WY)$")
  private String state;

  @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = INVALID_ZIP)
  private String zip;

  @NotBlank(message = "Password" + REQUIRED_FIELD)
  private String password;

  private String image;

  @ElementCollection
  private Set<Long> wishList = new HashSet<>();

  @ElementCollection
  private Set<Long> shoppingCart = new HashSet<>();

  public Customer() {
  }

  public Customer(@NotBlank(message = "First name" + REQUIRED_FIELD) String firstName,
      @NotBlank(message = "Last name" + REQUIRED_FIELD) String lastName,
      @Pattern(regexp = "^\\D?(\\d{3})\\D?\\D?(\\d{3})\\D?(\\d{4})$", message = INVALID_PHONE_NUMBER) String phone,
      @NotBlank(message = "Email"
          + REQUIRED_FIELD) @Email(message = INVALID_EMAIL) String email, String street,
      String city, @Pattern(regexp =
      "^(AL|AK|AR|AZ|CA|CO|CT|DC|DE|FL|GA|HI|IA|ID|IL|IN|KS|KY|LA|MA|MD|ME|MI|MN|MO|MS|MT|NC|ND|NE"
          + "|NH|NJ|NM|NV|NY|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VA|VT|WA|WI|WV|WY)$") String state,
      @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = INVALID_ZIP) String zip,
      @NotBlank(message = "Password"
          + REQUIRED_FIELD) String password, String image) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.password = password;
    this.image = image;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  public String getPassword() {
    return password;
  }

  public String getImage() {
    return image;
  }

  public Set<Long> getWishList() {
    return wishList;
  }

  public Set<Long> getShoppingCart() {
    return shoppingCart;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setWishList(Set<Long> wishList) {
    this.wishList = wishList;
  }

  public void addToWishList(Long productId) {
    this.wishList.add(productId);
  }

  public void setShoppingCart(Set<Long> shoppingCart) {
    this.shoppingCart = shoppingCart;
  }

  public void addToShoppingCart(Long productId) {this.shoppingCart.add(productId); }
}
