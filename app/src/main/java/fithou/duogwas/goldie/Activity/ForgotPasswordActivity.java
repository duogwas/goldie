package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.ErrorResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText edtForgotPasswordEmail;
    Button btnConfirm, btnReturnLogin;
    Dialog dialog;
    ProgressBar progressBar;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);
        AnhXa();
        setOnClick();
    }

    private void AnhXa() {
        edtForgotPasswordEmail = findViewById(R.id.edtForgotPasswordEmail);
        btnConfirm = findViewById(R.id.btnConfirm);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);
    }

    private void setOnClick() {
        btnConfirm.setOnClickListener(this);
    }

    private void ForgotPassword() {
        tvError.setVisibility(View.GONE);
        String email = edtForgotPasswordEmail.getText().toString();

        if (email.isEmpty()) {
            edtForgotPasswordEmail.setError("Vui lòng điền Email của bạn");
            edtForgotPasswordEmail.requestFocus();
        }

        if (edtForgotPasswordEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        UserService userService = ApiUtils.getUserAPIService();
        Call<String> call = userService.ForgotPassword(email);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setVisibility(View.VISIBLE);
                btnConfirm.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog = new Dialog(ForgotPasswordActivity.this);
                                    dialog.setContentView(R.layout.dialog_forgot_password_success);
                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                                    dialog.setCancelable(false);
                                    dialog.show();
                                    progressBar.setVisibility(View.GONE);
                                    btnConfirm.setVisibility(View.VISIBLE);
                                    btnReturnLogin = dialog.findViewById(R.id.btnReturnLogin);
                                    btnReturnLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                                            intent.putExtra("emailForgotPassword",email);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            }, 3000);
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
                            btnConfirm.setVisibility(View.VISIBLE);
                        }
                    }
                }, 3000);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Lỗi khi cấp lại mật khẩu", Toast.LENGTH_SHORT).show();
                Log.e("ForgotErr", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirm:
                ForgotPassword();
                break;

            default:
                break;
        }

    }
}