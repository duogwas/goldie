package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.Adapter.CategoryAdapter;
import fithou.duogwas.goldie.Adapter.ProductColorAdapter;
import fithou.duogwas.goldie.Adapter.ProductImageSliderAdapter;
import fithou.duogwas.goldie.Adapter.ProductSizeAdapter;
import fithou.duogwas.goldie.Entity.ProductImage;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.ProductResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    Long idProduct;
    SliderView imageSlider;
    TextView tvName, tvCodeAndSold, tvPrice, tvDescription, tvColor, tvSize;
    RecyclerView rcvColor, rcvSize;
    ProductImageSliderAdapter adapterImage;
    ProductSizeAdapter adapterSize;
    ProductColorAdapter adapterColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_detail);
        AnhXa();
        getIdProduct();
        LoadProductDetail();
    }

    private void AnhXa() {
        imageSlider = findViewById(R.id.imageSlider);
        tvName = findViewById(R.id.tvName);
        tvCodeAndSold = findViewById(R.id.tvCodeAndSold);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        tvColor = findViewById(R.id.tvColor);
        tvSize = findViewById(R.id.tvSize);
        rcvColor = findViewById(R.id.rcvColor);
        rcvSize = findViewById(R.id.rcvSize);
    }

    private void getIdProduct() {
        Intent intent = getIntent();
        idProduct = intent.getLongExtra("idProduct", -1);
    }

    private void LoadProductDetail() {
        ProductService productService = ApiUtils.getProductAPIService();
        Call<ProductResponse> call = productService.getProductDetail(idProduct);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse product = response.body();
                tvName.setText(product.getName());
                tvCodeAndSold.setText(product.getCode() + " | Đã bán " + product.getQuantitySold());
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedPrice = currencyFormat.format(product.getPrice());
                tvPrice.setText(formattedPrice);
                adapterImage = new ProductImageSliderAdapter(product.getProductImages(),ProductDetailActivity.this);
                LoadImage(adapterImage);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rcvColor.setLayoutManager(linearLayoutManager);
                adapterColor = new ProductColorAdapter(product.getProductColors(), ProductDetailActivity.this,adapterSize,rcvSize);
                rcvColor.setAdapter(adapterColor);
                tvDescription.setText((Html.fromHtml(product.getDescription())));
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });

    }

    private void LoadImage(ProductImageSliderAdapter productImageSliderAdapter) {
        imageSlider.setSliderAdapter(productImageSliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.WHITE);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSlider.startAutoCycle();
    }
}