package io.training.catalyte.sportsapparel.entities;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.training.catalyte.sportsapparel.constants.StringConstants.REQUIRED_FIELD;

public class Product {

    @Id
    private Long id;

    @NotNull(message = "Price" + REQUIRED_FIELD)
    private Float price;

    @NotBlank(message = "Name" + REQUIRED_FIELD)
    private String name;

    @NotBlank(message = "Description" + REQUIRED_FIELD)
    private String description;

    @NotBlank(message = "Demographic" + REQUIRED_FIELD)
    private String demographic;

    @NotBlank(message = "Category" + REQUIRED_FIELD)
    private String category;

    @NotBlank(message = "Type" + REQUIRED_FIELD)
    private String type;

    @NotBlank(message = "Release Date" + REQUIRED_FIELD)
    private String releaseDate;

    @NotBlank(message = "Primary Color Code" + REQUIRED_FIELD)
    private String primaryColorCode;

    @NotBlank(message = "Secondary Color Code" + REQUIRED_FIELD)
    private String secondaryColorCode;

    @NotBlank(message = "Style Number" + REQUIRED_FIELD)
    private String styleNumber;

    @NotBlank(message = "Global Product Code" + REQUIRED_FIELD)
    private String globalProductCode;

    public Product() {
    }

    public Product(Long id, @NotNull(message = "Price" + REQUIRED_FIELD) Float price,
                   @NotBlank(message = "Name" + REQUIRED_FIELD) String name,
                   @NotBlank(message = "Description" + REQUIRED_FIELD) String description,
                   @NotBlank(message = "Demographic" + REQUIRED_FIELD) String demographic,
                   @NotBlank(message = "Category" + REQUIRED_FIELD) String category,
                   @NotBlank(message = "Type" + REQUIRED_FIELD) String type,
                   @NotBlank(message = "Release Date" + REQUIRED_FIELD) String releaseDate,
                   @NotBlank(message = "Primary Color Code" + REQUIRED_FIELD) String primaryColorCode,
                   @NotBlank(message = "Secondary Color Code" + REQUIRED_FIELD) String secondaryColorCode,
                   @NotBlank(message = "Style Number" + REQUIRED_FIELD) String styleNumber,
                   @NotBlank(message = "Global Product Code" + REQUIRED_FIELD) String globalProductCode) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.demographic = demographic;
        this.category = category;
        this.type = type;
        this.releaseDate = releaseDate;
        this.primaryColorCode = primaryColorCode;
        this.secondaryColorCode = secondaryColorCode;
        this.styleNumber = styleNumber;
        this.globalProductCode = globalProductCode;
    }

    public Long getId() {
        return id;
    }

    public Float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDemographic() {
        return demographic;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPrimaryColorCode() {
        return primaryColorCode;
    }

    public String getSecondaryColorCode() {
        return secondaryColorCode;
    }

    public String getStyleNumber() {
        return styleNumber;
    }

    public String getGlobalProductCode() {
        return globalProductCode;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", price=" + price + ", name='" + name + '\''
                + ", description='" + description + '\'' + ", demographic='" + demographic + '\''
                + ", category='" + category + '\'' + ", type='" + type + '\'' + ", releaseDate='"
                + releaseDate + '\'' + ", primaryColorCode='" + primaryColorCode + '\''
                + ", secondaryColorCode='" + secondaryColorCode + '\'' + ", styleNumber='" + styleNumber
                + '\'' + ", globalProductCode='" + globalProductCode + '\'' + '}';
    }
}

