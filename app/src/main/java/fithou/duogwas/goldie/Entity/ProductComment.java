package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 03/03/2024.
//


import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ProductComment {
    private Long id;

    private Float star;

    private String content;

    private Product product;

    private User user;

    private List<ProductCommentImage> productCommentImages;
}
