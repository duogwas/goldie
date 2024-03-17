package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fithou.duogwas.goldie.Adapter.ProductAdapter;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.Page;
import fithou.duogwas.goldie.Response.ProductResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductByCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    String nameCategory;
    Long idCategory;
    String sort="";
    TextView tvName;
    RecyclerView rcvProduct;
    SearchView searchView;
    AppCompatButton btnFilter;
    ImageButton btnSort;
    ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_by_category);
        initView();
        getData();
        setOnClick();
    }

    private void initView() {
        tvName = findViewById(R.id.tvName);
        tvName.setAllCaps(true);
        searchView = findViewById(R.id.searchView);
        btnFilter = findViewById(R.id.btnFilter);
        btnSort = findViewById(R.id.btnSort);
        rcvProduct = findViewById(R.id.rcvProduct);
        imgBack = findViewById(R.id.imgBack);
    }

    private void getData() {
        Intent intent = getIntent();
        nameCategory = intent.getStringExtra("nameCategory");
        idCategory = intent.getLongExtra("idCategory", -1);
        tvName.setText(nameCategory);
        getProductByCategory(sort);
    }

    private void setOnClick() {
        btnSort.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }


    private void getProductByCategory(String sort) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductByCategoryActivity.this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        ProductService productService = ApiUtils.getProductAPIService();
        Call<Page<ProductResponse>> call = productService.getProductByCategory(idCategory, 0, 10, sort);
        call.enqueue(new Callback<Page<ProductResponse>>() {
            @Override
            public void onResponse(Call<Page<ProductResponse>> call, Response<Page<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    Page<ProductResponse> page = response.body();
                    List<ProductResponse> product = page.getContent();
                    ProductAdapter productAdapter = new ProductAdapter(product, ProductByCategoryActivity.this);
                    rcvProduct.setAdapter(productAdapter);
                } else {
                    Log.e("getProductByCategory", "Lỗi phản hồi không thành công");
                }

            }

            @Override
            public void onFailure(Call<Page<ProductResponse>> call, Throwable t) {
                Log.e("getProductByCategory", "Lỗi kết nối" + t.getMessage());
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bottom_sort);
        LinearLayout layoutNew = dialog.findViewById(R.id.layoutNew);
        LinearLayout layoutBest = dialog.findViewById(R.id.layoutBest);
        LinearLayout layoutCheap = dialog.findViewById(R.id.layoutCheap);
        LinearLayout layoutExpensive = dialog.findViewById(R.id.layoutExpensive);

        layoutNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort="id,desc";
                getProductByCategory(sort);
                dialog.dismiss();
            }
        });

        layoutBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort="quantitySold,desc";
                getProductByCategory(sort);
                dialog.dismiss();
            }
        });

        layoutCheap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort="price,asc";
                getProductByCategory(sort);
                dialog.dismiss();
            }
        });

        layoutExpensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort="price,desc";
                getProductByCategory(sort);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFilter:
                break;
            case R.id.btnSort:
                showDialog();
                break;
            case R.id.imgBack:
                finish();
                break;
            default:
                break;
        }
    }
}