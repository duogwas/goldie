package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import fithou.duogwas.goldie.Request.LoginDto;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Response.ErrorResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserService;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUsername, edtPassword;
    TextView tvForgotPassword, tvSignUp, tvError;
    Button btnSignIn;
    ProgressBar progressBar;
    String emailForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        AnhXa();
        setOnClick();
        getDataAfterForgotPassword();
    }

    private void AnhXa() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvError = findViewById(R.id.tvError);
        btnSignIn = findViewById(R.id.btnSignIn);
        progressBar=findViewById(R.id.progressBar);
    }

    private void setOnClick() {
        tvForgotPassword.setOnClickListener(SignInActivity.this);
        tvSignUp.setOnClickListener(SignInActivity.this);
        btnSignIn.setOnClickListener(SignInActivity.this);
    }

    private void getDataAfterForgotPassword() {
        Intent intent = getIntent();
        emailForgotPassword = intent.getStringExtra("emailForgotPassword");
        if (emailForgotPassword != "") {
            edtUsername.setText(emailForgotPassword);
        }
    }

    private void Login() {
        tvError.setVisibility(View.GONE);
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

        if (edtUsername.getText().toString().trim().isEmpty() ||
                edtPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(SignInActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginDto loginDto = new LoginDto(username, password, "string");
        UserService userService = ApiUtils.getUserAPIService();
        Call<TokenDto> call = userService.SignIn(loginDto);
        call.enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                progressBar.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.GONE);
                tvSignUp.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            TokenDto userSignIn = response.body();
                            UserManager.saveUser(SignInActivity.this, "User", "MODE_PRIVATE", userSignIn);
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            ToastPerfect.makeText(SignInActivity.this, ToastPerfect.SUCCESS, "Đăng nhập thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();;
                        } else {
                            ErrorResponse errorResponse = null;
                            try {
                                errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (errorResponse != null) {
                                String defaultMessage = errorResponse.getDefaultMessage();
                                tvError.setVisibility(View.VISIBLE);
                                tvError.setText(defaultMessage);
                            }
                            progressBar.setVisibility(View.GONE);
                            btnSignIn.setVisibility(View.VISIBLE);
                            tvSignUp.setVisibility(View.VISIBLE);
                        }
                    }
                },300);

            }

            @Override
            public void onFailure(Call<TokenDto> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Lỗi khi đăng nhập", Toast.LENGTH_SHORT).show();
                Log.e("SignInErr", t.getMessage());
            }
        });
    }

    private void LoginAfterForgotPassword() {
        String password = edtPassword.getText().toString();

        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng điền Mật khẩu của bạn");
            edtPassword.requestFocus();
        }

        if (edtUsername.getText().toString().trim().isEmpty() ||
                edtPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(SignInActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginDto loginDto = new LoginDto(emailForgotPassword, password, "string");
        UserService userService = ApiUtils.getUserAPIService();
        Call<TokenDto> call = userService.SignIn(loginDto);
        call.enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    ErrorResponse errorResponse = null;
                    try {
                        errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (errorResponse != null) {
                        String defaultMessage = errorResponse.getDefaultMessage();
                        Toast.makeText(SignInActivity.this, defaultMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenDto> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Lỗi khi đăng nhập", Toast.LENGTH_SHORT).show();
                Log.e("SignInErr", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSignIn:
                if (emailForgotPassword != "") {
                    Login();
                } else {
                    LoginAfterForgotPassword();
                }
                break;

            case R.id.tvForgotPassword:
                intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.tvSignUp:
                intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}