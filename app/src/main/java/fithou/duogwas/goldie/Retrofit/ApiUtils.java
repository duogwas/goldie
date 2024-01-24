package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 23/01/2024.
//


public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.1.61:8080/api/";

    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }

    public static UserService getUserAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
