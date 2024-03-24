package fithou.duogwas.goldie.Response;
//
// Created by duogwas on 21/03/2024.
//


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fithou.duogwas.goldie.Entity.InvoiceStatus;
import fithou.duogwas.goldie.Entity.PayType;
import fithou.duogwas.goldie.Entity.Status;

public class InvoiceResponse {
    private Long id;

    private Date createdDate;
    private String createdTime;

    private Double totalAmount;

    private String receiverName;

    private String phone;

    private String note;

    private String address;

    private PayType payType;

    private UserAdressResponse userAddress;

    private Status status;

    private List<InvoiceStatus> invoiceStatuses = new ArrayList<>();

    public InvoiceResponse(Long id, Date createdDate, String createdTime, Double totalAmount, String receiverName, String phone, String note, String address, PayType payType, UserAdressResponse userAddress, Status status, List<InvoiceStatus> invoiceStatuses) {
        this.id = id;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public UserAdressResponse getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAdressResponse userAddress) {
        this.userAddress = userAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<InvoiceStatus> getInvoiceStatuses() {
        return invoiceStatuses;
    }

    public void setInvoiceStatuses(List<InvoiceStatus> invoiceStatuses) {
        this.invoiceStatuses = invoiceStatuses;
    }
}
