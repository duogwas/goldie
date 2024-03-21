package fithou.duogwas.goldie.Response;
//
// Created by duogwas on 21/03/2024.
//


import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import fithou.duogwas.goldie.Entity.InvoiceStatus;
import fithou.duogwas.goldie.Entity.PayType;
import fithou.duogwas.goldie.Entity.Status;

public class InvoiceResponse {
    private Long id;

    private Date createdDate;

    private Double totalAmount;

    private String receiverName;

    private String phone;

    private String note;

    private String address;

    private PayType payType;

    private UserAdressResponse userAddress;

    private Status status;

    private List<InvoiceStatus> invoiceStatuses = new ArrayList<>();

    public InvoiceResponse(Long id, Date createdDate, Double totalAmount, String receiverName, String phone, String note, String address, PayType payType, UserAdressResponse userAddress, Status status, List<InvoiceStatus> invoiceStatuses) {
        this.id = id;
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
        this.receiverName = receiverName;
        this.phone = phone;
        this.note = note;
        this.address = address;
        this.payType = payType;
        this.userAddress = userAddress;
        this.status = status;
        this.invoiceStatuses = invoiceStatuses;
    }
}
