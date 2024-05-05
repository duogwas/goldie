package fithou.duogwas.goldie.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import fithou.duogwas.goldie.Activity.MainActivity;
import fithou.duogwas.goldie.Adapter.PrimaryCategoryAdapter;
import fithou.duogwas.goldie.Adapter.SearchCategoryAdapter;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.Page;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.CategoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    RecyclerView.Adapter adapterPrimaryCategories, adapterSearchCategories;
    RecyclerView rcvCategories;
    android.widget.SearchView searchView;
    ShimmerFrameLayout shimmerCategory;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setSearchView();
        loadCategories();
        refreshCountItemCart();
    }

    private void initView() {
        rcvCategories = getView().findViewById(R.id.rcvCategories);
        searchView = getView().findViewById(R.id.searchViewCategory);
        shimmerCategory = getView().findViewById(R.id.shimmerCategory);
        shimmerCategory.startShimmer();
    }

    private void refreshCountItemCart() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.countItemInCart();
        }
    }

    private void loadCategories() {
        CategoryService categoryService = ApiUtils.getCategoryAPIService();
        Call<List<CategoryResponse>> call = categoryService.findPrimaryCategory();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    List<CategoryResponse> categoryResponse = response.body();
                    rcvCategories.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rcvCategories.setLayoutManager(linearLayoutManager);
                    adapterPrimaryCategories = new PrimaryCategoryAdapter(categoryResponse, getContext());
                    rcvCategories.setAdapter(adapterPrimaryCategories);
                    shimmerCategory.hideShimmer();
                    shimmerCategory.stopShimmer();
                    shimmerCategory.setVisibility(View.GONE);
                } else {
                    Log.e("loadCategories", "response not successful");
                }

            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e("loadCategories", "onFailure: " + t.getMessage());
            }
        });
    }

    private void searchCategory(String categoryName) {
        CategoryService categoryService = ApiUtils.getCategoryAPIService();
        Call<Page<CategoryResponse>> call = categoryService.searchCategory(categoryName, 0, 10);
        call.enqueue(new Callback<Page<CategoryResponse>>() {
            @Override
            public void onResponse(Call<Page<CategoryResponse>> call, Response<Page<CategoryResponse>> response) {
                if(response.isSuccessful()){
                    Page<CategoryResponse> page = response.body();
                    List<CategoryResponse> categories = page.getContent();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rcvCategories.setLayoutManager(linearLayoutManager);
                    adapterSearchCategories = new SearchCategoryAdapter(categories, getContext());
                    rcvCategories.setAdapter(adapterSearchCategories);
                }
                else {
                    Log.e("searchCategory", "response not successful");
                }
            }

            @Override
            public void onFailure(Call<Page<CategoryResponse>> call, Throwable t) {
                Log.e("searchCategory", "onFailure: " + t.getMessage());
            }
        });

    }

    private void setSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() == 0) {
                    loadCategories();
                } else {
                    searchCategory(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() == 0) {
                    loadCategories();
                } else {
                    searchCategory(s);
                }
                return false;
            }
        });
    }
}