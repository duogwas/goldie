package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 27/01/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryService {
    @GET("category/public/findAllList")
    Call<List<CategoryResponse>> getCategoriesList();

    @GET("category/public/findPrimaryCategory")
    Call<List<CategoryResponse>> findPrimaryCategory();

    @GET("category/public/search")
    Call<Page<CategoryResponse>> searchCategory(@Query("q") String search,
                                                @Query("page") int page,
                                                @Query("size") int size);

}
