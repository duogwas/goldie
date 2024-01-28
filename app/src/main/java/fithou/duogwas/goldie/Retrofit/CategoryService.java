package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 27/01/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Request.LoginDto;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("category/public/findAllList")
    Call<List<CategoryResponse>> GetCategoriesList();

    @GET("category/public/findPrimaryCategory")
    Call<List<CategoryResponse>> findPrimaryCategory();
}
