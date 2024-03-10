package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 23/01/2024.
//


public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://172.16.15.244:8080/api/";

    public static UserService getUserAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static CategoryService getCategoryAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(CategoryService.class);
    }

    public static ProductService getProductAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ProductService.class);
    }
}
