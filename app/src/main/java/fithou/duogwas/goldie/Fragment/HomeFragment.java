package fithou.duogwas.goldie.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bdtopcoder.smart_slider.SliderAdapter;
import com.bdtopcoder.smart_slider.SliderItem;

import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.Adapter.CategoryAdapter;
import fithou.duogwas.goldie.Adapter.ProductAdapter;
import fithou.duogwas.goldie.Adapter.SearchCategoryAdapter;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.Page;
import fithou.duogwas.goldie.Response.ProductResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.CategoryService;
import fithou.duogwas.goldie.Retrofit.ProductService;
import fithou.duogwas.goldie.Retrofit.UserService;
import fithou.duogwas.goldie.Utils.ObjectSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class HomeFragment extends Fragment {
    ViewPager2 smartSlider;
    TokenDto user;
    TextView tvHiName;
    RecyclerView.Adapter adapterCategories, adapterProduct;
    RecyclerView rcvCategories, rcvNewProducts;

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
        AnhXa();
        Slide();
        LoadUserInfor();
        LoadCategoriesList();
        LoadProductList();
    }

    private void AnhXa() {
        smartSlider = getView().findViewById(R.id.smartSlider);
        tvHiName = getView().findViewById(R.id.tvHiName);
    }

    private void Slide() {
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.banner_1, "image 1"));
        sliderItems.add(new SliderItem(R.drawable.banner_2, "image 2"));
        smartSlider.setAdapter(new SliderAdapter(sliderItems, smartSlider, 3500));
    }

    private void LoadUserInfor() {
        user = ObjectSharedPreferences.getSavedObjectFromPreference(getContext(), "User", "MODE_PRIVATE", TokenDto.class);
        tvHiName.setText("Xin chào,\n" + user.getUser().getFullname());
    }

    private void LoadCategoriesList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvCategories = getView().findViewById(R.id.rcvCategories);
        rcvCategories.setLayoutManager(linearLayoutManager);
        CategoryService categoryService = ApiUtils.getCategoryAPIService();
        Call<List<CategoryResponse>> call = categoryService.GetCategoriesList();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                List<CategoryResponse> categoryResponse = response.body();
                adapterCategories = new CategoryAdapter(categoryResponse, getContext());
                rcvCategories.setAdapter(adapterCategories);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {

            }
        });
    }

    private void LoadProductList(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcvNewProducts=getView().findViewById(R.id.rcvNewProducts);
        rcvNewProducts.setLayoutManager(gridLayoutManager);
        ProductService productService = ApiUtils.getProductAPIService();
        Call<Page<ProductResponse>> call = productService.getProductPage(0, 10);
        call.enqueue(new Callback<Page<ProductResponse>>() {
            @Override
            public void onResponse(Call<Page<ProductResponse>> call, Response<Page<ProductResponse>> response) {
                if(response.isSuccessful()){
                    Page<ProductResponse> page = response.body();
                    List<ProductResponse> product = page.getContent();
                    adapterProduct = new ProductAdapter(product, getContext());
                    rcvNewProducts.setAdapter(adapterProduct);
                }
                else {
                    Log.e("loi1","loi k thanh cong");
                }

            }

            @Override
            public void onFailure(Call<Page<ProductResponse>> call, Throwable t) {
                Log.e("loi2","loi k ket noi" + t.getMessage());
            }
        });

    }
}