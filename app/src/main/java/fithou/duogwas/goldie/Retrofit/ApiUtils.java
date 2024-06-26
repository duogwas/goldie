package fithou.duogwas.goldie.Retrofit;
//
// Created by duogwas on 23/01/2024.
//


public class ApiUtils {
    private ApiUtils() {
    }

//        public static final String BASE_URL = "http://172.16.15.154:8080/api/"; //cty
    public static final String BASE_URL = "http://192.168.1.15:8080/api/";  //home
//    public static final String BASE_URL = "http://172.20.10.2:8080/api/";  //4g
//    public static final String BASE_URL = "http://192.168.2.129:8080/api/";  //home2
//    public static final String BASE_URL = "http://192.168.100.177:8080/api/";  //home


    public static UserService getUserAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static CategoryService getCategoryAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(CategoryService.class);
    }

    public static ProductService getProductAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ProductService.class);
    }

    public static UserAddressService getUserAddressAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(UserAddressService.class);
    }

    public static AddressService getAddressAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(AddressService.class);
    }

    public static InvoiceService getInvoiceAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(InvoiceService.class);
    }

    public static VoucherService getVoucherAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(VoucherService.class);
    }

    public static MoMoService getMomoAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(MoMoService.class);
    }
}
