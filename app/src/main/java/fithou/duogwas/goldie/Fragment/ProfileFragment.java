package fithou.duogwas.goldie.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import fithou.duogwas.goldie.Activity.SignInActivity;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Request.LoginDto;
import fithou.duogwas.goldie.Request.PasswordDto;
import fithou.duogwas.goldie.Response.ErrorResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserService;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    TextView tvChangePassword;
    AppCompatButton btnLogout;
    ConstraintLayout clOrder, clAddress;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setOnClick();
    }

    private void initView() {
        tvChangePassword = getView().findViewById(R.id.tvChangePassword);
        btnLogout = getView().findViewById(R.id.btnLogout);
        clOrder = getView().findViewById(R.id.clOrder);
        clAddress = getView().findViewById(R.id.clAddress);
    }

    private void setOnClick() {
        tvChangePassword.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        clOrder.setOnClickListener(this);
        clAddress.setOnClickListener(this);
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_password);
        ConstraintLayout clChangePassword = dialog.findViewById(R.id.clChangePassword);
        EditText edtOldPassword = dialog.findViewById(R.id.edtOldPassword);
        EditText edtNewPassword = dialog.findViewById(R.id.edtNewPassword);
        EditText edtReNewPassword = dialog.findViewById(R.id.edtReNewPassword);
        TextView tvError = dialog.findViewById(R.id.tvError);
        AppCompatButton btnChangePassword = dialog.findViewById(R.id.btnChangePassword);
        ConstraintLayout clChangePasswordSuccess = dialog.findViewById(R.id.clChangePasswordSuccess);
        AppCompatButton btnOk = dialog.findViewById(R.id.btnOk);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(edtOldPassword, edtNewPassword, edtReNewPassword, tvError, clChangePassword, clChangePasswordSuccess);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void changePassword(EditText edtOldPassword, EditText edtNewPassword, EditText edtReNewPassword, TextView tvError,
                                ConstraintLayout clChangePassword, ConstraintLayout clChangePasswordSuccess) {
        String oldPass = edtOldPassword.getText().toString();
        String newPass = edtNewPassword.getText().toString();
        String confirm = edtReNewPassword.getText().toString();

        if (oldPass.isEmpty()) {
            edtOldPassword.setError("Vui lòng điền Mật khẩu của bạn");
            edtOldPassword.requestFocus();
        }

        if (newPass.isEmpty()) {
            edtNewPassword.setError("Vui lòng điền Mật khẩu mới của bạn");
            edtNewPassword.requestFocus();
        }

        if (confirm.isEmpty()) {
            edtReNewPassword.setError("Vui lòng xác nhận Mật khẩu mới của bạn");
            edtReNewPassword.requestFocus();
        }

        if (edtOldPassword.getText().toString().trim().isEmpty() ||
                edtNewPassword.getText().toString().trim().isEmpty() ||
                edtNewPassword.getText().toString().trim().isEmpty()) {
            tvError.setText("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        TokenDto user = UserManager.getSavedUser(getContext(), "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        PasswordDto passwordDto = new PasswordDto(oldPass, newPass);
        UserService userService = ApiUtils.getUserAPIService();
        Call<String> call = userService.changePassword("Bearer " + token, passwordDto);
        if (newPass.equals(confirm)) {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        clChangePassword.setVisibility(View.GONE);
                        clChangePasswordSuccess.setVisibility(View.VISIBLE);
                    } else {
                        ErrorResponse errorResponse = null;
                        try {
                            errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (errorResponse != null) {
                            String defaultMessage = errorResponse.getDefaultMessage();
                            tvError.setText(defaultMessage);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("changePassword", t.getMessage());
                }
            });
        } else {
            tvError.setText("Mật khẩu mới và xác nhận mật khẩu không khớp!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvChangePassword:
                showDialog();
                break;

            case R.id.clOrder:
                break;

            case R.id.clAddress:
                break;

            case R.id.btnLogout:
                UserManager.clearSavedUser(getContext(), "User", "MODE_PRIVATE");
                startActivity(new Intent(getContext(), SignInActivity.class));
                break;

            default:
                break;
        }

    }
}