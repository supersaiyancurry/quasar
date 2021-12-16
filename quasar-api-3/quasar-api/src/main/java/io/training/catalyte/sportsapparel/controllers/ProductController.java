package io.training.catalyte.sportsapparel.controllers;

import io.training.catalyte.sportsapparel.entities.Product;
import io.training.catalyte.sportsapparel.services.ProductService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/demographic/{demographic}")
    public ResponseEntity<List<Product>> getByDemographic(@PathVariable String demographic) {
        return new ResponseEntity<>(productService.getByDemographic(demographic), HttpStatus.OK);
    }

    @GetMapping("/demographic/{demographic}/category/{category}")
    public ResponseEntity<List<Product>> getByDemographicAndCategory(@PathVariable String demographic,
                                                                     @PathVariable String category) {
        return new ResponseEntity<>(productService.getByDemographicAndCategory(demographic, category),
                HttpStatus.OK);
    }

    @GetMapping("/demographic/{demographic}/type/{type}")
    public ResponseEntity<List<Product>> getByDemographicAndType(@PathVariable String demographic,
                                                                 @PathVariable String type) {
        return new ResponseEntity<>(productService.getByDemographicAndType(demographic, type),
                HttpStatus.OK);
    }

    @GetMapping("/demographic/{demographic}/category/{category}/type/{type}")
    public ResponseEntity<List<Product>> getByDemographicCategoryType(
            @PathVariable String demographic, @PathVariable String category, @PathVariable String type) {
        return new ResponseEntity<>(productService.getByDemographicCategoryType(
                demographic, category, type), HttpStatus.OK);
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Product>> getByMultipleSearchTerms(@PathVariable String searchString) {
        return new ResponseEntity<>(productService.getByMultipleSearchTerms(searchString),
                HttpStatus.OK);
    }

    @GetMapping("/search-exact/{searchString}")
    public ResponseEntity<List<Product>> getBySearchTerm(@PathVariable String searchString) {
        return new ResponseEntity<>(productService.getBySearchTerm(searchString), HttpStatus.OK);
    }

    @GetMapping("/newest/demographic/{demographic}")
    public ResponseEntity<List<Product>> getNewestProductsByDemographic(
            @PathVariable String demographic
    ) {
        return new ResponseEntity<>(productService.getNewestProductsByDemographic(demographic),
                HttpStatus.OK);
    }

    @GetMapping("/newest/category/{category}")
    public ResponseEntity<List<Product>> getNewestProductsByDemographicCategoryPrice(
            @PathVariable String category) {
        return new ResponseEntity<>(productService.getNewestProductsByCategory(category),
                HttpStatus.OK);
    }

  @GetMapping(value = "distinctValue/{distinctValue}")
  public ResponseEntity<String> getProductsByDistinctValue(@PathVariable String distinctValue) {
    List<Product> demographicMatches = Arrays.asList();
    return new ResponseEntity<>(productService.getProductsByDistinctValue(distinctValue),
        HttpStatus.OK);
  }

}
