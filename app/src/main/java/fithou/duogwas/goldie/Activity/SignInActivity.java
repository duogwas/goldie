package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import fithou.duogwas.goldie.Model.User;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Retrofit.ApiService;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUsername, edtPassword;
    TextView tvForgotPassword, tvSignUp;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        AnhXa();
        setOnClick();
    }

    private void AnhXa() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void setOnClick() {
        tvForgotPassword.setOnClickListener(SignInActivity.this);
        tvSignUp.setOnClickListener(SignInActivity.this);
        btnSignIn.setOnClickListener(SignInActivity.this);
    }

    private void Login() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if (username.isEmpty()) {
            edtUsername.setError("Vui lòng điền Tên tài khoản của bạn");
            edtUsername.requestFocus();
        }

        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng điền Mật khẩu của bạn");
            edtPassword.requestFocus();
        }

        User userLoginRequest = new User(username, password);
        ApiService apiService = ApiUtils.getAPIService();
        Call<User> call = apiService.Login(userLoginRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignInActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignInActivity.this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("err",String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


//        ApiService.API_SERVICE.Login(username,password).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Log.e("Login code: ", String.valueOf(response.code()));
//                if(response.isSuccessful()){
//                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(SignInActivity.this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSignIn:
                Login();
                break;

//            case R.id.tvForgotPassword:
//                intent = new Intent(SignInActivity.this, ChooseRole.class);
//                startActivity(intent);
//                finish();
//                break;

            case R.id.tvSignUp:
                intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }


}