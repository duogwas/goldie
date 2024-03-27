package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 21/03/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Request.InvoiceRequest;
import fithou.duogwas.goldie.Request.UserAdressRequest;
import fithou.duogwas.goldie.Response.InvoiceDetailResponse;
import fithou.duogwas.goldie.Response.InvoiceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InvoiceService {
    @POST("invoice/user/create")
    Call<InvoiceResponse> creatInvoice(@Header("Authorization") String authToken,
                                       @Body InvoiceRequest invoiceRequest);

    @GET("invoice/user/find-by-user")
    Call<List<InvoiceResponse>> getListInvoice(@Header("Authorization") String authToken);

    @GET("invoice/user/find-by-id")
    Call<InvoiceResponse> getInvoiceById(@Header("Authorization") String authToken,
                                         @Query("idInvoice") Long idInvoice);

    @GET("invoice-detail/user/find-by-invoice")
    Call<List<InvoiceDetailResponse>> getInvoiceDetail(@Header("Authorization") String authToken,
                                                       @Query("idInvoice") Long idInvoice);

}
