package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 18/03/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Response.UserAdressResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserAddressService {
    @GET("user-address/user/my-address")
    Call<List<UserAdressResponse>> getListAddress(@Header("Authorization") String authToken,
                                                  @Query("page") int page,
                                                  @Query("size") int size);
}
