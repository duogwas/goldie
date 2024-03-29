package fithou.duogwas.goldie.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.Activity.FullProductActivity;
import fithou.duogwas.goldie.Activity.MainActivity;
import fithou.duogwas.goldie.Adapter.CategoryAdapter;
import fithou.duogwas.goldie.Adapter.ProductAdapter;
import fithou.duogwas.goldie.Adapter.ProductImageSliderAdapter;
import fithou.duogwas.goldie.Entity.ProductImage;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.Page;
import fithou.duogwas.goldie.Response.ProductResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.CategoryService;
import fithou.duogwas.goldie.Retrofit.ProductService;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, View.OnClickListener {
    SliderView smartSlider;
    TokenDto user;
    TextView tvHiName, tvSeeAllProduct;
    SearchView searchViewProduct;
    RecyclerView.Adapter adapterCategories, adapterProduct;
    RecyclerView rcvCategories, rcvNewProducts;
    ShimmerFrameLayout shimmerProduct, shimmerCategory;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        sliderBanner();
        setOnClick();
        loadUserInfor();
        loadCategoriesList();
        loadProductList();
        refreshCountItemCart();
    }

    private void initView() {
        smartSlider = getView().findViewById(R.id.smartSlider);
        tvHiName = getView().findViewById(R.id.tvHiName);
        tvSeeAllProduct = getView().findViewById(R.id.tvSeeAllProduct);
        searchViewProduct = getView().findViewById(R.id.searchViewProduct);
        rcvNewProducts = getView().findViewById(R.id.rcvNewProducts);
        rcvCategories = getView().findViewById(R.id.rcvCategories);
        shimmerProduct = getView().findViewById(R.id.shimmerProduct);
        shimmerProduct.startShimmer();
        shimmerCategory = getView().findViewById(R.id.shimmerCategory);
        shimmerCategory.startShimmer();
    }

    private void sliderBanner() {
        List<ProductImage> sliderItems = new ArrayList<>();
        sliderItems.add(new ProductImage("https://res.cloudinary.com/dxketb89r/image/upload/v1711637484/banner_1_hzvxqm.png"));
        sliderItems.add(new ProductImage("https://res.cloudinary.com/dxketb89r/image/upload/v1711637484/banner_1_hzvxqm.png"));

        ProductImageSliderAdapter adapterImage = new ProductImageSliderAdapter(sliderItems, getContext());
        smartSlider.setSliderAdapter(adapterImage);
        smartSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        smartSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        smartSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        smartSlider.setIndicatorSelectedColor(Color.WHITE);
        smartSlider.setIndicatorUnselectedColor(Color.GRAY);
        smartSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        smartSlider.startAutoCycle();
    }

    private void setOnClick() {
        tvSeeAllProduct.setOnClickListener(this);
        searchViewProduct.setOnQueryTextListener(this);
    }

    private void loadUserInfor() {
        user = UserManager.getSavedUser(getContext(), "User", "MODE_PRIVATE", TokenDto.class);
        tvHiName.setText("Xin chào,\n" + user.getUser().getFullname());
    }

    private void refreshCountItemCart() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.countItemInCart();
        }
    }

    private void loadCategoriesList() {
        CategoryService categoryService = ApiUtils.getCategoryAPIService();
        Call<List<CategoryResponse>> call = categoryService.GetCategoriesList();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    List<CategoryResponse> categoryResponse = response.body();
                    rcvCategories.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rcvCategories.setLayoutManager(linearLayoutManager);
                    adapterCategories = new CategoryAdapter(categoryResponse, getContext());
                    rcvCategories.setAdapter(adapterCategories);
                    shimmerCategory.hideShimmer();
                    shimmerCategory.stopShimmer();
                    shimmerCategory.setVisibility(View.GONE);
                } else {
                    Log.e("LoadCategoriesListIndex", "response not successful");
                }

            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e("LoadCategoriesListIndex", "onFailure: " + t.getMessage());
            }
        });
    }

    private void loadProductList() {
        ProductService productService = ApiUtils.getProductAPIService();
        Call<Page<ProductResponse>> call = productService.getProductPage(0, 10, "");
        call.enqueue(new Callback<Page<ProductResponse>>() {
            @Override
            public void onResponse(Call<Page<ProductResponse>> call, Response<Page<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    Page<ProductResponse> page = response.body();
                    List<ProductResponse> product = page.getContent();
                    rcvNewProducts.setVisibility(View.VISIBLE);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    rcvNewProducts.setLayoutManager(gridLayoutManager);
                    adapterProduct = new ProductAdapter(product, getContext());
                    rcvNewProducts.setAdapter(adapterProduct);
                    shimmerProduct.hideShimmer();
                    shimmerProduct.stopShimmer();
                    shimmerProduct.setVisibility(View.GONE);
                } else {
                    Log.e("loadProductListIndex", "response not successful");
                }

            }

            @Override
            public void onFailure(Call<Page<ProductResponse>> call, Throwable t) {
                Log.e("loadProductListIndex", "onFailure: " + t.getMessage());
            }
        });

    }

    private void findProductByParam(String param) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvNewProducts.setLayoutManager(gridLayoutManager);
        ProductService productService = ApiUtils.getProductAPIService();
        Call<Page<ProductResponse>> call = productService.getProductByParam(param, 0, 10);
        call.enqueue(new Callback<Page<ProductResponse>>() {
            @Override
            public void onResponse(Call<Page<ProductResponse>> call, Response<Page<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    Page<ProductResponse> page = response.body();
                    List<ProductResponse> product = page.getContent();
                    ProductAdapter productAdapter = new ProductAdapter(product, getContext());
                    rcvNewProducts.setAdapter(productAdapter);
                } else {
                    Log.e("findProductByParam", "response not successful");
                }

            }

            @Override
            public void onFailure(Call<Page<ProductResponse>> call, Throwable t) {
                Log.e("findProductByParam", "onFailure: " + t.getMessage());
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSeeAllProduct:
                Intent intent = new Intent(getContext(), FullProductActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s.length() == 0) {
            loadProductList();
        } else {
            findProductByParam(s);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() == 0) {
            loadProductList();
        } else {
            findProductByParam(s);
        }
        return false;
    }
}