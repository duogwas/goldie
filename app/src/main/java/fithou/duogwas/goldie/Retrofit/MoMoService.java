package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 22/03/2024.
//


import fithou.duogwas.goldie.Request.InvoiceRequest;
import fithou.duogwas.goldie.Request.PaymentDto;
import fithou.duogwas.goldie.Response.ResponsePayment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MoMoService {
    @POST("urlpayment")
    Call<ResponsePayment> requestPaymentMomo(@Header("Authorization") String authToken,
                                             @Body PaymentDto paymentDto);
}
