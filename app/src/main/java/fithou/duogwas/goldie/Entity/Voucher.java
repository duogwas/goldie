package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 21/03/2024.
//


import java.sql.Date;

public class Voucher {
    private Long id;

    private String code;

    private String name;

    private Double discount;

    private Double minAmount;

    private Date startDate;

    private Date endDate;

    private Boolean block;
}
