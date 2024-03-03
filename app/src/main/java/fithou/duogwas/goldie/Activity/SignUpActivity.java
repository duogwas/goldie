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

import fithou.duogwas.goldie.Entity.User;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.ErrorResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtFullName, edtPhone, edtEmail, edtPassword;
    TextView tvSignIn, tvError;
    Button btnSignUp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        AnhXa();
        setOnClick();
    }

    private void AnhXa() {
        edtFullName = findViewById(R.id.edtFullName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvSignIn = findViewById(R.id.tvSignIn);
        tvError = findViewById(R.id.tvError);
        btnSignUp = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setOnClick() {
        tvSignIn.setOnClickListener(SignUpActivity.this);
        btnSignUp.setOnClickListener(SignUpActivity.this);
    }

    private void SignUp() {
        tvError.setVisibility(View.GONE);
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String fullname = edtFullName.getText().toString();
        String phone = edtPhone.getText().toString();

        if (fullname.isEmpty()) {
            edtFullName.setError("Vui lòng điền Họ tên của bạn");
            edtFullName.requestFocus();
        }

        if (phone.isEmpty()) {
            edtPhone.setError("Vui lòng điền Số điện thoại của bạn");
            edtPhone.requestFocus();
        }

        if (email.isEmpty()) {
            edtEmail.setError("Vui lòng điền Email của bạn");
            edtEmail.requestFocus();
        }

        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng điền Mật khẩu của bạn");
            edtPassword.requestFocus();
        }

        if (edtEmail.getText().toString().trim().isEmpty() ||
                edtPassword.getText().toString().trim().isEmpty() ||
                edtFullName.getText().toString().trim().isEmpty() ||
                edtPhone.getText().toString().trim().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        User userRegis = new User(email, password, fullname, phone);
        UserService userService = ApiUtils.getUserAPIService();
        Call<User> call = userService.SignUp(userRegis);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressBar.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.GONE);
                tvSignIn.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, ActiveAccountActivity.class);
                            intent.putExtra("emailRegis", email);
                            intent.putExtra("passwordRegis", password);
                            startActivity(intent);
                            finish();
                            Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
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
                            btnSignUp.setVisibility(View.VISIBLE);
                            tvSignIn.setVisibility(View.VISIBLE);
                        }
                    }
                }, 300);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Lỗi khi đăng ký", Toast.LENGTH_SHORT).show();
                Log.e("SignUpErr", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSignUp:
                SignUp();
                break;

            case R.id.tvSignIn:
                intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}