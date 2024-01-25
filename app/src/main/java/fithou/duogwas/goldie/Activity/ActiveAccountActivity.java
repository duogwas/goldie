package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveAccountActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtInputCode1, edtInputCode2, edtInputCode3, edtInputCode4, edtInputCode5, edtInputCode6;
    Button btnActive;
    String emailRegis, passwordRegis;
    TextView tvError;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_active_account);
        AnhXa();
        getDataVerify();
        setOnClick();
        setupOtpInput();
    }

    private void AnhXa() {
        btnActive = findViewById(R.id.btnActive);
        edtInputCode1 = findViewById(R.id.edtInputCode1);
        edtInputCode2 = findViewById(R.id.edtInputCode2);
        edtInputCode3 = findViewById(R.id.edtInputCode3);
        edtInputCode4 = findViewById(R.id.edtInputCode4);
        edtInputCode5 = findViewById(R.id.edtInputCode5);
        edtInputCode6 = findViewById(R.id.edtInputCode6);
        tvError = findViewById(R.id.tvError);
        progressBar = findViewById(R.id.progressBar);
    }

    private void getDataVerify() {
        Intent intent = getIntent();
        emailRegis = intent.getStringExtra("emailRegis");
        passwordRegis = intent.getStringExtra("passwordRegis");
    }

    private void setupOtpInput() {
        edtInputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtInputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtInputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtInputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtInputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtInputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtInputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtInputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtInputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtInputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setOnClick() {
        btnActive.setOnClickListener(this);
    }

    private void verifyUser() {
        tvError.setVisibility(View.GONE);
        if (edtInputCode1.getText().toString().trim().isEmpty() ||
                edtInputCode2.getText().toString().trim().isEmpty() ||
                edtInputCode3.getText().toString().trim().isEmpty() ||
                edtInputCode4.getText().toString().trim().isEmpty() ||
                edtInputCode5.getText().toString().trim().isEmpty() ||
                edtInputCode6.getText().toString().trim().isEmpty()) {
            Toast.makeText(ActiveAccountActivity.this, "Vui lòng nhập mã xác thực", Toast.LENGTH_SHORT).show();
            return;
        }

        String key = edtInputCode1.getText().toString() +
                edtInputCode2.getText().toString() +
                edtInputCode3.getText().toString() +
                edtInputCode4.getText().toString() +
                edtInputCode5.getText().toString() +
                edtInputCode6.getText().toString();

        UserService userService = ApiUtils.getUserAPIService();
        Call<String> call = userService.ActiveAccount(emailRegis, key);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setVisibility(View.VISIBLE);
                btnActive.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            signInAfterVerify();
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
                            btnActive.setVisibility(View.VISIBLE);
                        }
                    }
                }, 300);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ActiveAccountActivity.this, "Lỗi khi acctive", Toast.LENGTH_SHORT).show();
                Log.e("ActiveErr", t.getMessage());
            }
        });
    }

    private void signInAfterVerify() {
        LoginDto loginDto = new LoginDto(emailRegis, passwordRegis, "string");
        UserService userService = ApiUtils.getUserAPIService();
        Call<TokenDto> call1 = userService.SignIn(loginDto);
        call1.enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ActiveAccountActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(ActiveAccountActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
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
                }
            }

            @Override
            public void onFailure(Call<TokenDto> call, Throwable t) {
                Toast.makeText(ActiveAccountActivity.this, "Lỗi khi đăng nhập sau xác thực", Toast.LENGTH_SHORT).show();
                Log.e("SignInAfterVerifyErr", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnActive:
                verifyUser();
                break;

            default:
                break;
        }
    }
}