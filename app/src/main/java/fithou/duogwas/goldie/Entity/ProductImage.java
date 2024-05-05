package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 03/03/2024.
//


import java.io.Serializable;

public class ProductImage implements Serializable {
    private Long id;

    private String linkImage;

    private Product product;

    public ProductImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
