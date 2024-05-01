package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fithou.duogwas.goldie.Adapter.MyAddressAdapter;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Response.UserAdressResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserAddressService;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAddressActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton btnCreateAddress;
    RecyclerView rcvMyAddress;
    ImageView ivBack;
    ShimmerFrameLayout shimmerMyAddress;
    ConstraintLayout clNoAddress;
    int checkIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_address);
        initView();
        setOnClick();
        getMyAddress();
        checkIntent();
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        btnCreateAddress = findViewById(R.id.btnCreateAddress);
        rcvMyAddress = findViewById(R.id.rcvMyAddress);
        clNoAddress = findViewById(R.id.clNoAddress);
        shimmerMyAddress = findViewById(R.id.shimmerMyAddress);
        shimmerMyAddress.startShimmer();
    }

    private void setOnClick() {
        ivBack.setOnClickListener(this);
        btnCreateAddress.setOnClickListener(this);
    }

    private void checkIntent() {
        Intent intent = getIntent();
        checkIntent = intent.getIntExtra("changeAdd", -1);
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
                    if(userAdressResponses.size()==0){
                        clNoAddress.setVisibility(View.VISIBLE);
                        shimmerMyAddress.hideShimmer();
                        shimmerMyAddress.stopShimmer();
                        shimmerMyAddress.setVisibility(View.GONE);
                    }else {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAddressActivity.this, LinearLayoutManager.VERTICAL, false);
                        rcvMyAddress.setLayoutManager(linearLayoutManager);
                        MyAddressAdapter myAddressAdapter = new MyAddressAdapter(userAdressResponses, MyAddressActivity.this, checkIntent);
                        rcvMyAddress.setAdapter(myAddressAdapter);
                        rcvMyAddress.setVisibility(View.VISIBLE);
                        shimmerMyAddress.hideShimmer();
                        shimmerMyAddress.stopShimmer();
                        shimmerMyAddress.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("getMyAddress", "response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<UserAdressResponse>> call, Throwable t) {
                Log.e("getMyAddress", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                startActivity(new Intent(MyAddressActivity.this, MainActivity.class));
                break;

            case R.id.btnCreateAddress:
                Intent intent = new Intent(MyAddressActivity.this, AddressDetailActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }

    }
}