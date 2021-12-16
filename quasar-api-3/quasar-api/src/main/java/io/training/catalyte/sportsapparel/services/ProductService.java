package io.training.catalyte.sportsapparel.services;

import io.training.catalyte.sportsapparel.entities.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {

    List<Product> getByDemographic(String demographic);

    List<Product> getByDemographicAndCategory(String demographic, String category);

    List<Product> getByDemographicAndType(String demographic, String type);

    List<Product> getByDemographicCategoryType(String demographic, String category, String type);

    List<Product> getNewestProductsByDemographic(String demographic);

    List<Product> getNewestProductsByCategory(String category);

    List<Product> getBySearchTerm(String searchString);

    List<Product> getByMultipleSearchTerms(String searchString);

    Set<Product> getProductsFromList(Set<Long> wishList);

  String getProductsByDistinctValue(String distinctValue);

}
