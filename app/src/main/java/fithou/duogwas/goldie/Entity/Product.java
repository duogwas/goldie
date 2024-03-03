package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 03/03/2024.
//


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private Long id;
    private String code;
    private String alias;
    private String name;
    private String imageBanner;
    private Double price;
    private String description;
    private Date createdDate;
    private Time createdTime;
    private Integer quantitySold;
    private List<ProductColor> productColors = new ArrayList<>();
    private List<ProductCategory> productCategories = new ArrayList<>();
    private List<ProductImage> productImages = new ArrayList<>();
    private List<ProductComment> productComments;
}
