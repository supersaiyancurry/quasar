package io.training.catalyte.sportsapparel.services;
import static io.training.catalyte.sportsapparel.constants.StringConstants.DEFAULT_HEROKU_URL;
import static io.training.catalyte.sportsapparel.constants.StringConstants.MAX_PAGE_SIZE;
import static io.training.catalyte.sportsapparel.constants.StringConstants.PAGE_NUMBER;
import static io.training.catalyte.sportsapparel.constants.StringConstants.PAGE_SIZE;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.training.catalyte.sportsapparel.entities.Product;
import io.training.catalyte.sportsapparel.exceptions.DataNotFound;
import io.training.catalyte.sportsapparel.exceptions.ServerError;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ProductServiceImpl implements ProductService {
  private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
  /**
   * Gets a list of products that match a specified demographic.
   *
   * @param demographic - a string representing which demographic to filter by
   * @return a List of Products
   */
  @Override
  public List<Product> getByDemographic(String demographic) {
    String dem = demographic.substring(0, 1).toUpperCase() + demographic.substring(1);
    String urlString = DEFAULT_HEROKU_URL + "?demographic=" + dem + PAGE_SIZE + PAGE_NUMBER;
    return getProducts(urlString);
  }
  /**
   * Gets a list of products that match a specified demographic and category.
   *
   * @param demographic - a string representing which demographic to filter by
   * @param category    - a string representing which category to filter by
   * @return a List of Products
   */
  @Override
  public List<Product> getByDemographicAndCategory(String demographic, String category) {
    String dem = demographic.substring(0, 1).toUpperCase() + demographic.substring(1);
    String cat = category.substring(0, 1).toUpperCase() + category.substring(1);
    String urlString =
            DEFAULT_HEROKU_URL + "?demographic=" + dem + "&category=" + cat + PAGE_SIZE + PAGE_NUMBER;
    try {
      return getProducts(urlString);
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * Gets a list of products that match a specified demographic and category.
   *
   * @param demographic - a string representing which demographic to filter by
   * @param type        - a string representing which type to filter by
   * @return a List of Products
   */
  @Override
  public List<Product> getByDemographicAndType(String demographic, String type) {
    String dem = demographic.substring(0, 1).toUpperCase() + demographic.substring(1);
    String typ = type.substring(0, 1).toUpperCase() + type.substring(1);
    String urlString =
            DEFAULT_HEROKU_URL + "?demographic=" + dem + "&type=" + typ + PAGE_SIZE + PAGE_NUMBER;
    try {
      return getProducts(urlString);
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * Gets a list of products that match a specified demographic, category, and type.
   *
   * @param demographic - a string representing which demographic to filter by
   * @param category    - a string representing which category to filter by
   * @param type        - a string representing which type to filter by
   * @return a List of Products
   */
  @Override
  public List<Product> getByDemographicCategoryType(String demographic, String category,
                                                    String type) {
    String dem = demographic.substring(0, 1).toUpperCase() + demographic.substring(1);
    String typ = type.substring(0, 1).toUpperCase() + type.substring(1);
    String cat = category.substring(0, 1).toUpperCase() + category.substring(1);
    String urlString =
            DEFAULT_HEROKU_URL + "?demographic=" + dem + "&category=" + cat + "&type=" + typ +
                    PAGE_SIZE + PAGE_NUMBER;
    try {
      return getProducts(urlString);
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * Gets a list of the five newest products by demographic.
   *
   * @param demographic - a string representing which demographic to filter by.
   * @return A list of the five newest products filtered by demographic.
   */
  @Override
  public List<Product> getNewestProductsByDemographic(String demographic) {
    String dem = demographic.substring(0, 1).toUpperCase() + demographic.substring(1);
    try {
      String urlString = DEFAULT_HEROKU_URL + "/types/newest" + "?demographic=" + dem;
      return getProducts(urlString);
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * Gets a list of the five newest products by category.
   *
   * @param category - a string representing which category to filter by.
   * @return A list of five newest products filtered by category.
   */
  @Override
  public List<Product> getNewestProductsByCategory(String category) {
    String cat = category.substring(0, 1).toUpperCase() + category.substring(1);
    try {
      String urlString = DEFAULT_HEROKU_URL + "/types/newest" + "?category=" + cat;
      return getProducts(urlString);
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * Gets a list of products that match a search string
   *
   * @param searchString A string to match to products
   * @return A list of matching products
   */
  @Override
  public List<Product> getBySearchTerm(String searchString) {
    try {
      return getSearchResults(searchString.toLowerCase());
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * Gets a list of products that match a search string
   *
   * @param searchString A string to match to products
   * @return A list of matching products
   */
  @Override
  public List<Product> getByMultipleSearchTerms(String searchString) {
    String[] splitString = searchString.toLowerCase().split(" ");
    List<Product> products = new ArrayList<>();
    // get a list from all products, then compare if those products contain at least one search string
    try {
      for (Product product : getAllProducts()) {
        for (String s : splitString) {
          if (product.getDescription().contains(s)) {
            products.add(product);
            break;
          }
        }
      }
      return products;
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * sends a request to the product search service to get a product by its id
   *
   * @param id the id of the product to fetch
   * @return the product retrieved from the product search API
   */
  public Product getProductById(Long id) {
    String urlString = DEFAULT_HEROKU_URL + "/" + id;
    JsonObject jsonObject;
    try {
      URL url = new URL(urlString);
      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
      String jsonString = br.readLine();
      jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new ServerError();
    }
    Gson gson = new Gson();
    return gson.fromJson(jsonObject, Product.class);
  }
  /**
   * Loops through a list of product ids and sends a request for the product data
   *
   * @param wishList a list of product ids to get
   * @return a list of Product entities
   */
  public Set<Product> getProductsFromList(Set<Long> wishList) {
    Set<Product> products = new HashSet<>();
    for (Long productId : wishList) {
      products.add(getProductById(productId));
    }
    return products;
  }
  /**
   * Helper function that gets products from deployed backend given a search string
   *
   * @param searchString a string entered by user
   * @return a list of Products retrieved
   */
  private List<Product> getSearchResults(String searchString) {
    List<Product> products = new ArrayList<>();
    try {
      for (Product product : getAllProducts()) {
        if (product.getDescription().toLowerCase().contains(searchString)) {
          products.add(product);
        }
      }
      return products;
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * gets a list of distinct values that are equal to a products category, type, or demographic
   *
   * @param distinctValue - a list of categories, types, or demographics
   * @return - a list of distinct values
   */
  @Override
  public String getProductsByDistinctValue(String distinctValue) {
    String productUrl
            = "https://v2-product-search-service.herokuapp.com/v1/products/distinctValues/"
            + distinctValue;
    ResponseEntity<String> response = restTemplate().getForEntity(
            productUrl, String.class, distinctValue);
    return response.getBody();
  }
  /**
   * Helper function that gets all products from deployed backend
   *
   * @return a list of all Products
   */
  private List<Product> getAllProducts() {
    List<Product> products = new ArrayList<>();
    String[] demographics = {"Men", "Women", "Kids"};
    try {
      for (String demographic : demographics) {
        products.addAll(getProducts(
                DEFAULT_HEROKU_URL + "?demographic=" + demographic + MAX_PAGE_SIZE + PAGE_NUMBER));
      }
      return products;
    } catch (Exception e) {
      throw new ServerError();
    }
  }
  /**
   * Helper function that gets the products from a deployed backend given the URL string
   *
   * @param urlString a string for the URL
   * @return a list of Products retrieved
   */
  private List<Product> getProducts(String urlString) {
    List<Product> products = new ArrayList<>();
    Gson gson = new Gson();
    //https://ampersandacademy.com/tutorials/json/java-json-get-json-reponse-url
    try {
      URL url = new URL(urlString);
      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
      String jsonString = br.readLine();
      JsonArray jsonArray;
      if (jsonString.startsWith("content", 2)) {
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        jsonArray = jsonObject.get("content").getAsJsonArray();
      } else {
        jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
      }
      if (jsonArray.size() == 0) {
        throw new DataNotFound();
      }
      //https://www.baeldung.com/gson-string-to-jsonobject
      for (JsonElement j : jsonArray) {
        Product product = gson.fromJson(j, Product.class);
        products.add(product);
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }
    return products;
  }
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}