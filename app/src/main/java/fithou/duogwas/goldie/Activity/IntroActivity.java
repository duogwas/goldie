package fithou.duogwas.goldie.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import fithou.duogwas.goldie.Entity.User;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Request.LoginDto;
import fithou.duogwas.goldie.Response.ErrorResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserService;
import fithou.duogwas.goldie.Utils.DeviceTokenManager;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class IntroActivity extends AppCompatActivity {
    TextView tvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        AnhXa();
        getDeviceToken();
        tvStartClick();
    }

    private void AnhXa() {
        tvStart = findViewById(R.id.tvStart);
    }

    private void tvStartClick() {
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User isLoged = UserManager.getSavedUser(IntroActivity.this, "User", "MODE_PRIVATE", User.class);
                if (isLoged != null) {
//                    loginAgain();
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(IntroActivity.this, SignInActivity.class));
                }
            }
        });
    }

    private void loginAgain() {
        TokenDto savedUser = UserManager.getSavedUser(IntroActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String username = savedUser.getUser().getUsername();
        String password = savedUser.getUser().getPassword();
        String tokenFCM = DeviceTokenManager.getDeviceToken(IntroActivity.this);

        LoginDto loginDto = new LoginDto(username, password, tokenFCM);
        UserService userService = ApiUtils.getUserAPIService();
        Call<TokenDto> call = userService.SignIn(loginDto);
        call.enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
//                    ToastPerfect.makeText(IntroActivity.this, ToastPerfect.SUCCESS, "Đăng nhập thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(IntroActivity.this, username.toLowerCase(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenDto> call, Throwable t) {
                Toast.makeText(IntroActivity.this, "Lỗi khi đăng nhập", Toast.LENGTH_SHORT).show();
                Log.e("SignInErr", t.getMessage());
            }
        });
    }

    private void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("TAG", token);

                        //save token
                        DeviceTokenManager.saveDeviceToken(IntroActivity.this, token);
                    }
                });
    }
}