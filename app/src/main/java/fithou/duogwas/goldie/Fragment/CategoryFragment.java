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
import android.widget.Toast;

import java.util.List;

import fithou.duogwas.goldie.Adapter.CategoryAdapter;
import fithou.duogwas.goldie.Adapter.PrimaryCategoryAdapter;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.Page;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.CategoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private RecyclerView.Adapter adapterPrimaryCategories, adapterSubCategories;
    RecyclerView rcvPrimaryCategories;
    android.widget.SearchView searchView;

    public CategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
        setSearchView();
        LoadPrimaryCategories();
    }

    private void AnhXa() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvPrimaryCategories = getView().findViewById(R.id.rcvPrimaryCategories);
        rcvPrimaryCategories.setLayoutManager(linearLayoutManager);
        searchView = getView().findViewById(R.id.searchViewCategory);
    }

    private void LoadPrimaryCategories() {
        CategoryService categoryService = ApiUtils.getCategoryAPIService();
        Call<List<CategoryResponse>> call = categoryService.findPrimaryCategory();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                List<CategoryResponse> categoryResponse = response.body();
                adapterPrimaryCategories = new PrimaryCategoryAdapter(categoryResponse, getContext());
                rcvPrimaryCategories.setAdapter(adapterPrimaryCategories);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {

            }
        });
    }

    private void SearchCategory(String categoryName) {
        CategoryService categoryService = ApiUtils.getCategoryAPIService();
        Call<Page<CategoryResponse>> call = categoryService.search(categoryName, 0, 10);
        call.enqueue(new Callback<Page<CategoryResponse>>() {
            @Override
            public void onResponse(Call<Page<CategoryResponse>> call, Response<Page<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    Page<CategoryResponse> page = response.body();
                    List<CategoryResponse> categories = page.getContent();
                    adapterPrimaryCategories = new PrimaryCategoryAdapter(categories, getContext());
                    rcvPrimaryCategories.setAdapter(adapterPrimaryCategories);
                } else {
                    Log.e("category", String.valueOf(response.code()));
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin1", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Page<CategoryResponse>> call, Throwable t) {
                Log.e("category2", t.getMessage());
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin2", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() == 0) {
                    LoadPrimaryCategories();
                } else {
                    SearchCategory(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() == 0) {
                    LoadPrimaryCategories();
                } else {
                    SearchCategory(s);
                }
                return false;
            }
        });
    }

}