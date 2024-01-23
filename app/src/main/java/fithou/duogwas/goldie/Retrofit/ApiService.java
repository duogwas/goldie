package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 23/01/2024.
//


import fithou.duogwas.goldie.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    Call<User> Login(@Body User user);
}
