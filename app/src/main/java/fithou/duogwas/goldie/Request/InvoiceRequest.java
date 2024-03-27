package fithou.duogwas.goldie.Request;
//
// Created by duogwas on 21/03/2024.
//


import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.Entity.PayType;

public class InvoiceRequest {
    private PayType payType;

    private String requestIdMomo;

    private String orderIdMomo;

    private Long userAddressId;

    private String voucherCode;

    private String note;

    private List<ProductSizeRequest> listProductSize = new ArrayList<>();

    public InvoiceRequest(PayType payType, String requestIdMomo, String orderIdMomo, Long userAddressId, String voucherCode, String note, List<ProductSizeRequest> listProductSize) {
        this.payType = payType;
        this.requestIdMomo = requestIdMomo;
        this.orderIdMomo = orderIdMomo;
        this.userAddressId = userAddressId;
        this.voucherCode = voucherCode;
        this.note = note;
        this.listProductSize = listProductSize;
    }

    public InvoiceRequest(PayType payType, Long userAddressId, String voucherCode, String note, List<ProductSizeRequest> listProductSize) {
        this.payType = payType;
        this.userAddressId = userAddressId;
        this.voucherCode = voucherCode;
        this.note = note;
        this.listProductSize = listProductSize;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public String getRequestIdMomo() {
        return requestIdMomo;
    }

    public void setRequestIdMomo(String requestIdMomo) {
        this.requestIdMomo = requestIdMomo;
    }

    public String getOrderIdMomo() {
        return orderIdMomo;
    }

    public void setOrderIdMomo(String orderIdMomo) {
        this.orderIdMomo = orderIdMomo;
    }

    public Long getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Long userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ProductSizeRequest> getListProductSize() {
        return listProductSize;
    }

    public void setListProductSize(List<ProductSizeRequest> listProductSize) {
        this.listProductSize = listProductSize;
    }
}
