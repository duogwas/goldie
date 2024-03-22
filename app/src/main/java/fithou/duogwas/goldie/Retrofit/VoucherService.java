package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 22/03/2024.
//


import fithou.duogwas.goldie.Entity.Voucher;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VoucherService {
    @GET("voucher/public/findByCode")
    Call<Voucher> checkVoucher(@Query("code") String code,
                               @Query("amount") Double amount);
}
