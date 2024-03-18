package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 23/01/2024.
//


public class ApiUtils {
    private ApiUtils() {}

//    public static final String BASE_URL = "http://172.16.15.175:8080/api/"; //cty
    public static final String BASE_URL = "http://192.168.1.61:8080/api/";  //home
//    public static final String BASE_URL = "http://172.20.10.4:8080/api/";  //4g
//    public static final String BASE_URL = "http://192.168.2.128:8080/api/";  //home2

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
