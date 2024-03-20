package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 24/01/2024.
//


import fithou.duogwas.goldie.Request.LoginDto;
import fithou.duogwas.goldie.Entity.User;
import fithou.duogwas.goldie.Request.PasswordDto;
import fithou.duogwas.goldie.Response.TokenDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @POST("login")
    Call<TokenDto> SignIn(@Body LoginDto loginDto);

    @POST("regis")
    Call<User> SignUp(@Body User user);

    @POST("active-account")
    Call<String> ActiveAccount(@Query("email") String email,
                               @Query("key") String key);

    @POST("forgot-password")
    Call<String> ForgotPassword(@Query("email") String email);

    @POST("user/change-password")
    Call<String> changePassword(@Header("Authorization") String authToken,
                                @Body PasswordDto passwordDto);
}
