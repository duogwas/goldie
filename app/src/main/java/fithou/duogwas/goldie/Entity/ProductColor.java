package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 03/03/2024.
//


import java.util.ArrayList;
import java.util.List;

public class ProductColor {
    private Long id;

    private String colorName;

    private String linkImage;

    private Product product;

    private List<ProductSize> productSizes = new ArrayList<>();
}
