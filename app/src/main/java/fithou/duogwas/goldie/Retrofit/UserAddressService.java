package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 18/03/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Request.UserAdressRequest;
import fithou.duogwas.goldie.Response.UserAdressResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAddressService {
    @GET("user-address/user/my-address")
    Call<List<UserAdressResponse>> getListAddress(@Header("Authorization") String authToken,
                                                  @Query("page") int page,
                                                  @Query("size") int size);

    @POST("user-address/user/create")
    Call<UserAdressResponse> creatAddress(@Header("Authorization") String authToken,
                                          @Body UserAdressRequest userAdressRequest);

    @POST("user-address/user/update")
    Call<UserAdressResponse> updateAddress(@Header("Authorization") String authToken,
                                           @Body UserAdressRequest userAdressRequest);

    @DELETE("user-address/user/delete")
    Call<Void> deleteAddress(@Header("Authorization") String authToken,
                             @Query("id") Long id);

    @GET("user-address/user/findById")
    Call<UserAdressResponse> getAddressDetail(@Header("Authorization") String authToken,
                                              @Query("id") Long id);
}
