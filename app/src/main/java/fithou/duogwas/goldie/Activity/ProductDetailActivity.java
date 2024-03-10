package fithou.duogwas.goldie.Activity;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fithou.duogwas.goldie.Adapter.CategoryAdapter;
import fithou.duogwas.goldie.Adapter.ProductColorAdapter;
import fithou.duogwas.goldie.Adapter.ProductImageSliderAdapter;
import fithou.duogwas.goldie.Adapter.ProductSizeAdapter;
import fithou.duogwas.goldie.Entity.Product;
import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.Entity.ProductColor;
import fithou.duogwas.goldie.Entity.ProductImage;
import fithou.duogwas.goldie.Entity.ProductSize;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.ProductResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.ProductService;
import fithou.duogwas.goldie.Utils.CartManager;
import fithou.duogwas.goldie.Utils.ObjectSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Long idProduct, idColorCart, idSizeCart;
    Double priceProduct;
    SliderView imageSlider;
    TextView tvName, tvCodeAndSold, tvPrice, tvDescription, tvColorName, tvSizeName, tvQuantity, tvTotalPrice, tvAddToCart;
    RecyclerView rcvColor, rcvSize;
    ProductImageSliderAdapter adapterImage;
    ProductSizeAdapter adapterSize;
    ProductColorAdapter adapterColor;
    ImageView ivMinus, ivPlus, ivShowDescription;
    ConstraintLayout clSize, clDescription;
    ProductResponse productResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_detail);
        initView();
        setOnClick();
        getIdProduct();
        LoadProductDetail();

    }

    private void initView() {
        imageSlider = findViewById(R.id.imageSlider);
        tvName = findViewById(R.id.tvName);
        tvCodeAndSold = findViewById(R.id.tvCodeAndSold);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        tvColorName = findViewById(R.id.tvColorName);
        tvSizeName = findViewById(R.id.tvSizeName);
        rcvColor = findViewById(R.id.rcvColor);
        rcvSize = findViewById(R.id.rcvSize);
        ivMinus = findViewById(R.id.ivMinus);
        ivPlus = findViewById(R.id.ivPlus);
        tvAddToCart = findViewById(R.id.tvAddToCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvQuantity = findViewById(R.id.tvQuantity);
        clSize = findViewById(R.id.clSize);
        ivShowDescription = findViewById(R.id.ivShowDescription);
        clDescription = findViewById(R.id.clDescription);
    }

    private void getIdProduct() {
        Intent intent = getIntent();
        idProduct = intent.getLongExtra("idProduct", -1);
        priceProduct = intent.getDoubleExtra("priceProduct", -1);
        productResponse = (ProductResponse) intent.getSerializableExtra("productResponse");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(priceProduct);
        tvTotalPrice.setText(formattedPrice);
    }

    private void setOnClick() {
        ivMinus.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        tvAddToCart.setOnClickListener(this);
        clDescription.setOnClickListener(this);
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
                adapterImage = new ProductImageSliderAdapter(product.getProductImages(), ProductDetailActivity.this);
                LoadImage(adapterImage);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rcvColor.setLayoutManager(linearLayoutManager);
                adapterColor = new ProductColorAdapter(product.getProductColors(), ProductDetailActivity.this, tvColorName, clSize, tvSizeName);
                rcvColor.setAdapter(adapterColor);
                tvDescription.setText((Html.fromHtml(product.getDescription())));
                adapterColor.setOnColorClickListener(new ProductColorAdapter.OnColorClickListener() {
                    @Override
                    public void onColorClick(Long idColor) {
                        idColorCart = idColor;
                        getSizeByColor(idColorCart);
                    }
                });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMinus:
                int number1 = parseInt(tvQuantity.getText().toString()) - 1;
                if (number1 >= 1) {
                    tvQuantity.setText(String.valueOf(number1));
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    String formattedPrice = currencyFormat.format(priceProduct * number1);
                    tvTotalPrice.setText(formattedPrice);
                }
                break;

            case R.id.ivPlus:
                int number2 = parseInt(tvQuantity.getText().toString()) + 1;
                tvQuantity.setText(String.valueOf(number2));
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedPrice = currencyFormat.format(priceProduct * number2);
                tvTotalPrice.setText(formattedPrice);
                break;

            case R.id.clDescription:
                if (tvDescription.getVisibility() == View.GONE) {
                    View vieww = findViewById(R.id.clDescription);
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) vieww.getLayoutParams();
                    layoutParams.setMargins(0, 15, 0, 0);
                    view.setLayoutParams(layoutParams);
                    ivShowDescription.setImageResource(R.drawable.ic_close);
                    tvDescription.setVisibility(View.VISIBLE);
                } else {
                    ivShowDescription.setImageResource(R.drawable.ic_next);
                    tvDescription.setVisibility(View.GONE);
                    View vieww = findViewById(R.id.clDescription);
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) vieww.getLayoutParams();
                    layoutParams.setMargins(0, 15, 0, 350);
                    view.setLayoutParams(layoutParams);
                }
                break;

            case R.id.tvAddToCart:
                addToCart(productResponse);
                break;

            default:
                break;
        }
    }


    private void getSizeByColor(Long idColorr) {
        ProductService productService = ApiUtils.getProductAPIService();
        Call<List<ProductSize>> call = productService.getSizeByColor(idColorr);
        call.enqueue(new Callback<List<ProductSize>>() {
            @Override
            public void onResponse(Call<List<ProductSize>> call, Response<List<ProductSize>> response) {
                List<ProductSize> size = response.body();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rcvSize.setLayoutManager(linearLayoutManager);
                adapterSize = new ProductSizeAdapter(size, ProductDetailActivity.this, tvSizeName);
                rcvSize.setAdapter(adapterSize);

                adapterSize.setOnSizeClickListener(new ProductSizeAdapter.OnSizeClickListener() {
                    @Override
                    public void onSizeClick(Long idSize) {
                        idSizeCart = idSize;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ProductSize>> call, Throwable t) {

            }
        });
    }


    private void addToCart(ProductResponse product) {
        List<ProductCart> productCartList = CartManager.getCart(this);
        ProductColor color = null;
        ProductSize size = null;
        int quantity;
        quantity = parseInt(tvQuantity.getText().toString());

        List<ProductColor> listColor = product.getProductColors();
        for (ProductColor productColor : listColor) {
            if (productColor.getId().equals(idColorCart)) {
                color = productColor;
                break;
            }
        }

        if (color == null) {
//            Toast.makeText(this, "clnull", Toast.LENGTH_SHORT).show();
            return;
        }

        List<ProductSize> listSize = color.getProductSizes();
        for (ProductSize productSize : listSize) {
            if (productSize.getId().equals(idSizeCart)) {
                size = productSize;
                break;
            }
        }
        if (size == null) {
//            Toast.makeText(this, "sznull", Toast.LENGTH_SHORT).show();
            return;
        }
//
//        ProductCart productCart = new ProductCart(product, color, size, quantity);
//        productCartList.add(productCart);

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        boolean productAlreadyInCart = false;
        for (ProductCart cartProduct : productCartList) {
            if (cartProduct.getProduct().getId().equals(product.getId()) &&
                    cartProduct.getColor().getId().equals(color.getId()) &&
                    cartProduct.getSize().getId().equals(size.getId())) {
                // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng
                cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                productAlreadyInCart = true;
                break;
            }
        }

        // Nếu sản phẩm chưa có trong giỏ hàng, thêm sản phẩm mới vào giỏ hàng
        if (!productAlreadyInCart) {
            ProductCart productCart = new ProductCart(product, color, size, quantity);
            productCartList.add(productCart);
        }

        // Lưu danh sách sản phẩm cart vào SharedPreferences
        CartManager.saveCart(this, productCartList);
        ToastPerfect.makeText(ProductDetailActivity.this, ToastPerfect.SUCCESS, "Thêm vào giỏ hàng thành công", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
    }
}