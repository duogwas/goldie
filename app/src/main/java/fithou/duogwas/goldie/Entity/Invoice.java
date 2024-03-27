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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public List<InvoiceStatus> getInvoiceStatuses() {
        return invoiceStatuses;
    }

    public void setInvoiceStatuses(List<InvoiceStatus> invoiceStatuses) {
        this.invoiceStatuses = invoiceStatuses;
    }
}
