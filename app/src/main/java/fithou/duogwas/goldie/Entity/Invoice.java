package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 21/03/2024.
//


import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private Long id;

    private Date createdDate;

    private Double totalAmount;

    private String receiverName;

    private String phone;

    private String address;

    private String note;

    private PayType payType;

    private UserAddress userAddress;

    private Status status;

    private Voucher voucher;

    private List<InvoiceStatus> invoiceStatuses = new ArrayList<>();
}
