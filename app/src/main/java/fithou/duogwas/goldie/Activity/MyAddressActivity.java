package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fithou.duogwas.goldie.Adapter.CategoryAdapter;
import fithou.duogwas.goldie.Adapter.UserAddressAdapter;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Response.UserAdressResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserAddressService;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class MyAddressActivity extends AppCompatActivity implements View.OnClickListener {
    Long idAddr;
    FloatingActionButton btnCreateAddress;
    RecyclerView rcvMyAddress;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_address);
        initView();
        setOnClick();
        getMyAddress();
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        btnCreateAddress = findViewById(R.id.btnCreateAddress);
        rcvMyAddress = findViewById(R.id.rcvMyAddress);
    }

    private void setOnClick() {
        ivBack.setOnClickListener(this);
        btnCreateAddress.setOnClickListener(this);
    }

    private void getMyAddress() {
        TokenDto user = UserManager.getSavedUser(MyAddressActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        UserAddressService userAddressService = ApiUtils.getUserAddressAPIService();
        Call<List<UserAdressResponse>> call = userAddressService.getListAddress("Bearer " + token, 0, 10);
        call.enqueue(new Callback<List<UserAdressResponse>>() {
            @Override
            public void onResponse(Call<List<UserAdressResponse>> call, Response<List<UserAdressResponse>> response) {
                if (response.isSuccessful()) {
                    List<UserAdressResponse> userAdressResponses = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAddressActivity.this, LinearLayoutManager.VERTICAL, false);
                    rcvMyAddress.setLayoutManager(linearLayoutManager);
                    UserAddressAdapter userAddressAdapter = new UserAddressAdapter(userAdressResponses, MyAddressActivity.this);
                    rcvMyAddress.setAdapter(userAddressAdapter);
                } else {
                    Toast.makeText(MyAddressActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserAdressResponse>> call, Throwable t) {
                Toast.makeText(MyAddressActivity.this, "2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;

            case R.id.btnCreateAddress:
                startActivity(new Intent(MyAddressActivity.this,MyAddressDetailActivity.class));
                break;

            default:
                break;
        }

    }
}