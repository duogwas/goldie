package fithou.duogwas.goldie.Request;
//
// Created by duogwas on 22/03/2024.
//


import java.util.ArrayList;
import java.util.List;

public class PaymentDto {
    private String codeVoucher;
    private String content;
    private String returnUrl;
    private String notifyUrl;
    private List<ProductSizeRequest> listProductSize = new ArrayList<>();

    public PaymentDto(String codeVoucher, String content, String returnUrl, String notifyUrl, List<ProductSizeRequest> listProductSize) {
        this.codeVoucher = codeVoucher;
        this.content = content;
        this.returnUrl = returnUrl;
        this.notifyUrl = notifyUrl;
        this.listProductSize = listProductSize;
    }
}
