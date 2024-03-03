package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 03/03/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.Page;
import fithou.duogwas.goldie.Response.ProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {
    @GET("product/public/findAll")
    Call<Page<ProductResponse>> getProductPage(@Query("page") int page,
                                               @Query("size") int size);
}
