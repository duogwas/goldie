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

import com.google.gson.Gson;

import fithou.duogwas.goldie.Model.User;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.ErrorResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtFullName, edtPhone, edtEmail, edtPassword;
    TextView tvSignIn;
    Button btnSignUp;

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
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void setOnClick() {
        tvSignIn.setOnClickListener(SignUpActivity.this);
        btnSignUp.setOnClickListener(SignUpActivity.this);
    }

    private void SignUp() {
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

        User userRegis = new User(email, password, fullname, phone);
        UserService userService = ApiUtils.getUserAPIService();
        Call<User> call = userService.SignUp(userRegis);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Intent intent = new Intent(SignUpActivity.this,ActiveAccountActivity.class);
                    intent.putExtra("emailRegis", email);
                    intent.putExtra("passwordRegis", password);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Response không thành công, chuyển đổi thành ErrorResponse
                    ErrorResponse errorResponse = null;
                    try {
                        errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (errorResponse != null) {
                        String defaultMessage = errorResponse.getDefaultMessage();
                        Toast.makeText(SignUpActivity.this, defaultMessage, Toast.LENGTH_SHORT).show();
                    }
                }
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
                finish();
                break;

            default:
                break;
        }
    }
}