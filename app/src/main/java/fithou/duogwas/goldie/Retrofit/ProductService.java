package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 03/03/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Entity.ProductSize;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.Page;
import fithou.duogwas.goldie.Response.ProductResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductService {
    @GET("product/public/findAll")
    Call<Page<ProductResponse>> getProductPage(@Query("page") int page,
                                               @Query("size") int size,
                                               @Query("sort") String sort);

    @GET("product/public/findById")
    Call<ProductResponse> getProductDetail(@Query("id") Long id);

    @GET("product/public/findByCategory")
    Call<Page<ProductResponse>> getProductByCategory(@Query("idCategory") long idCategory,
                                                     @Query("page") int page,
                                                     @Query("size") int size,
                                                     @Query("sort") String sort);

    @GET("product-size/public/find-by-product-color")
    Call<List<ProductSize>> getSizeByColor(@Query("idProColor") Long idProColor);

    @POST("product/public/searchFull")
    Call<Page<ProductResponse>> filterProduct(@Query("smallPrice") double smallPrice,
                                              @Query("largePrice") double largePrice,
                                              @Query("page") int page,
                                              @Query("size") int size,
                                              @Body List<Long> listIdCategory);

    @GET("product/public/findByParam")
    Call<Page<ProductResponse>> getProductByParam(@Query("q") String q,
                                                  @Query("page") int page,
                                                  @Query("size") int size);
}
