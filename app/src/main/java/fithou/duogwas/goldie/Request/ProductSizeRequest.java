package fithou.duogwas.goldie.Request;
//
// Created by duogwas on 21/03/2024.
//


public class ProductSizeRequest {
    private Long idProductSize;

    private Integer quantity;

    public ProductSizeRequest(Long idProductSize, Integer quantity) {
        this.idProductSize = idProductSize;
        this.quantity = quantity;
    }

    public Long getIdProductSize() {
        return idProductSize;
    }

    public void setIdProductSize(Long idProductSize) {
        this.idProductSize = idProductSize;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
