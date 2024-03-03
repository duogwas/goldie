package fithou.duogwas.goldie.Response;
//
// Created by duogwas on 03/03/2024.
//


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.Entity.ProductCategory;
import fithou.duogwas.goldie.Entity.ProductColor;
import fithou.duogwas.goldie.Entity.ProductImage;

public class ProductResponse {
    private Long id;
    private String code;
    private String alias;
    private String name;
    private String imageBanner;
    private Double price;
    private String description;
//    private Date createdDate;
//    private Time createdTime;
    private Integer quantitySold;
    private List<ProductColor> productColors = new ArrayList<>();
    private List<ProductCategory> productCategories = new ArrayList<>();
    private List<ProductImage> productImages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageBanner() {
        return imageBanner;
    }

    public void setImageBanner(String imageBanner) {
        this.imageBanner = imageBanner;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Time getCreatedTime() {
//        return createdTime;
//    }
//
//    public void setCreatedTime(Time createdTime) {
//        this.createdTime = createdTime;
//    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public List<ProductColor> getProductColors() {
        return productColors;
    }

    public void setProductColors(List<ProductColor> productColors) {
        this.productColors = productColors;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }
}
