package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 21/03/2024.
//


import fithou.duogwas.goldie.Request.InvoiceRequest;
import fithou.duogwas.goldie.Request.UserAdressRequest;
import fithou.duogwas.goldie.Response.InvoiceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InvoiceService {
    @POST("invoice/user/create")
    Call<InvoiceResponse> creatInvoice(@Header("Authorization") String authToken,
                                       @Body InvoiceRequest invoiceRequest);

}
