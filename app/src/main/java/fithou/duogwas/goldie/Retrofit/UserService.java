package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 24/01/2024.
//


import fithou.duogwas.goldie.Model.LoginDto;
import fithou.duogwas.goldie.Model.User;
import fithou.duogwas.goldie.Request.TokenDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Call<TokenDto> SignIn(@Body LoginDto loginDto);

    @POST("regis")
    Call<User> SignUp(@Body User user);
}
