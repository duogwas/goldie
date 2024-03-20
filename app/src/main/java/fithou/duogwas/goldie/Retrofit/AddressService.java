package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 19/03/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Entity.Province;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AddressService {
    @GET("address/public/province")
    Call<List<Province>> getProvinceList();
}
