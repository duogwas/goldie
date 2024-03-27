package fithou.duogwas.goldie.Activity;

import androidx.annotation.NonNull;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.Adapter.DialogCategoryAdapter;
import fithou.duogwas.goldie.Adapter.ProductAdapter;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.Page;
import fithou.duogwas.goldie.Response.ProductResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.CategoryService;
import fithou.duogwas.goldie.Retrofit.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullProductActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    String sort;
    RecyclerView rcvProduct;
    SearchView searchView;
    AppCompatButton btnFilter;
    ImageButton btnSort;
    ImageView ivBack;
    FrameLayout FrameLayout;
    DialogCategoryAdapter dialogCategoryAdapter;
    List<Long> listIdCategory = new ArrayList<Long>();
    double smallPrice, largePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_product);
        initView();
        setOnClick();
        getFullProduct(sort);
    }

    private void initView() {
        rcvProduct = findViewById(R.id.rcvProduct);
        searchView = findViewById(R.id.searchView);
        btnFilter = findViewById(R.id.btnFilter);
        btnSort = findViewById(R.id.btnSort);
        rcvProduct = findViewById(R.id.rcvProduct);
        ivBack = findViewById(R.id.ivBack);
        FrameLayout = findViewById(R.id.FrameLayout);
    }

    private void setOnClick() {
        btnSort.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
    }

    private void getFullProduct(String sort) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FullProductActivity.this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        ProductService productService = ApiUtils.getProductAPIService();
        Call<Page<ProductResponse>> call = productService.getProductPage(0, 10, sort);
        call.enqueue(new Callback<Page<ProductResponse>>() {
            @Override
            public void onResponse(Call<Page<ProductResponse>> call, Response<Page<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    Page<ProductResponse> page = response.body();
                    List<ProductResponse> product = page.getContent();
                    ProductAdapter productAdapter = new ProductAdapter(product, FullProductActivity.this);
                    rcvProduct.setAdapter(productAdapter);
                } else {
                    Log.e("getFullProduct", "Lỗi phản hồi không thành công");
                }

            }

            @Override
            public void onFailure(Call<Page<ProductResponse>> call, Throwable t) {
                Log.e("getFullProduct", "Lỗi kết nối" + t.getMessage());
            }
        });
    }

    private void showDialogSort() {
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
                sort = "id,desc";
                getFullProduct(sort);
                dialog.dismiss();
            }
        });

        layoutBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "quantitySold,desc";
                getFullProduct(sort);
                dialog.dismiss();
            }
        });

        layoutCheap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "price,asc";
                getFullProduct(sort);
                dialog.dismiss();
            }
        });

        layoutExpensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "price,desc";
                getFullProduct(sort);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showDialogFilter() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_filter_product);
        RecyclerView rcvCategory = dialog.findViewById(R.id.rcvCategory);
        RangeSlider rangeSlider = dialog.findViewById(R.id.rangePrice);
        AppCompatButton btnFilter = dialog.findViewById(R.id.btnFilter);
        TextView tvMinPrice = dialog.findViewById(R.id.tvMinPrice);
        TextView tvMaxPrice = dialog.findViewById(R.id.tvMaxPrice);
        loadCategoriesDialog(rcvCategory);
        rangeSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedPrice = currencyFormat.format(value);
                return formattedPrice;
            }
        });

        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> values = slider.getValues();
                float minValue = values.get(0); // giá trị min
                float maxValue = values.get(1); // giá trị max
                smallPrice = minValue;
                largePrice = maxValue;
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedMinPrice = currencyFormat.format(minValue);
                tvMinPrice.setText(formattedMinPrice);
                String formattedMaxPrice = currencyFormat.format(maxValue);
                tvMaxPrice.setText(formattedMaxPrice);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterProduct(smallPrice, largePrice, listIdCategory);
                Toast.makeText(FullProductActivity.this, "list: " + listIdCategory.toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private void loadCategoriesDialog(RecyclerView rcv) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FullProductActivity.this, 2);
        rcv.setLayoutManager(gridLayoutManager);
        CategoryService categoryService = ApiUtils.getCategoryAPIService();
        Call<List<CategoryResponse>> call = categoryService.GetCategoriesList();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                List<CategoryResponse> categoryResponse = response.body();
                dialogCategoryAdapter = new DialogCategoryAdapter(categoryResponse, FullProductActivity.this);
                rcv.setAdapter(dialogCategoryAdapter);
                dialogCategoryAdapter.setOnCbCategoryListener(new DialogCategoryAdapter.OnCbCategoryListener() {
                    @Override
                    public void onCheckBoxClick(List<Long> idCategory) {
                        listIdCategory = idCategory;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {

            }
        });
    }

    private void filterProduct(double smallPrice, double largePrice, List<Long> listIdCategory) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FullProductActivity.this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        ProductService productService = ApiUtils.getProductAPIService();
        Call<Page<ProductResponse>> call = productService.filterProduct(smallPrice, largePrice, 0, 10, listIdCategory);
        call.enqueue(new Callback<Page<ProductResponse>>() {
            @Override
            public void onResponse(Call<Page<ProductResponse>> call, Response<Page<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    Page<ProductResponse> page = response.body();
                    List<ProductResponse> product = page.getContent();
                    ProductAdapter productAdapter = new ProductAdapter(product, FullProductActivity.this);
                    rcvProduct.setAdapter(productAdapter);
                } else {
                    Log.e("getFullProduct", "Lỗi phản hồi không thành công");
                }

            }

            @Override
            public void onFailure(Call<Page<ProductResponse>> call, Throwable t) {
                Log.e("getFullProduct", "Lỗi kết nối" + t.getMessage());
            }
        });
    }

    private void findProductByParam(String param) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FullProductActivity.this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        ProductService productService = ApiUtils.getProductAPIService();
        Call<Page<ProductResponse>> call = productService.getProductByParam(param, 0, 10);
        call.enqueue(new Callback<Page<ProductResponse>>() {
            @Override
            public void onResponse(Call<Page<ProductResponse>> call, Response<Page<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    Page<ProductResponse> page = response.body();
                    List<ProductResponse> product = page.getContent();
                    ProductAdapter productAdapter = new ProductAdapter(product, FullProductActivity.this);
                    rcvProduct.setAdapter(productAdapter);
                } else {
                    Log.e("findProductByParam", "Lỗi phản hồi không thành công");
                }

            }

            @Override
            public void onFailure(Call<Page<ProductResponse>> call, Throwable t) {
                Log.e("findProductByParam", "Lỗi kết nối" + t.getMessage());
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFilter:
                showDialogFilter();
                break;
            case R.id.btnSort:
                showDialogSort();
                break;
            case R.id.ivBack:
                startActivity(new Intent(FullProductActivity.this,MainActivity.class));
                finish();
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s.length() == 0) {
            getFullProduct(sort);
        } else {
            findProductByParam(s);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() == 0) {
            getFullProduct(sort);
        } else {
            findProductByParam(s);
        }
        return false;
    }
}