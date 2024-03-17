package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 10/03/2024.
//


import java.util.List;
import java.util.Objects;

import fithou.duogwas.goldie.Response.ProductResponse;

public class ProductCart {
    private static int nextId = 0;  // Biến static để theo dõi ID tiếp theo
    private long id;  // ID của CartItem
    ProductResponse product;
    ProductColor color;
    ProductSize size;
    int quantity;

    public ProductCart(ProductResponse product, ProductColor color, ProductSize size, int quantity) {
        this.id = generateId(product.getId(), color.getId(), size.getId());
        this.product = product;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    private int generateId(long productId, long colorId, long sizeId) {
        return Objects.hash(productId, colorId, sizeId);
    }

    public long getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        ProductCart.nextId = nextId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public void setProduct(ProductResponse product) {
        this.product = product;
    }

    public ProductColor getColor() {
        return color;
    }

    public void setColor(ProductColor color) {
        this.color = color;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
