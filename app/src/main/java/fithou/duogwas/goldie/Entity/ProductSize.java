package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 03/03/2024.
//


import java.io.Serializable;

public class ProductSize implements Serializable {
    private Long id;

    private String sizeName;

    private Integer quantity;

    private ProductColor productColor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductColor getProductColor() {
        return productColor;
    }

    public void setProductColor(ProductColor productColor) {
        this.productColor = productColor;
    }
}
