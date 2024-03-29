package fithou.duogwas.goldie.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fithou.duogwas.goldie.Adapter.CartAdapter;
import fithou.duogwas.goldie.Entity.PayType;
import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.Entity.Voucher;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Request.InvoiceRequest;
import fithou.duogwas.goldie.Request.PaymentDto;
import fithou.duogwas.goldie.Request.ProductSizeRequest;
import fithou.duogwas.goldie.Response.ErrorResponse;
import fithou.duogwas.goldie.Response.InvoiceResponse;
import fithou.duogwas.goldie.Response.ResponsePayment;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Response.UserAdressResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.InvoiceService;
import fithou.duogwas.goldie.Retrofit.MoMoService;
import fithou.duogwas.goldie.Retrofit.UserAddressService;
import fithou.duogwas.goldie.Retrofit.VoucherService;
import fithou.duogwas.goldie.Utils.CartManager;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    createOrderMomo();
                }
            }
    );
    UserAdressResponse addressDefault = null;
    int changeAdd, createAdd;
    String requestIdMomo = null;
    String orderIdMomo = null;
    String voucher = null;
    Double discount = 0.0;
    Double shipPrice = 20000.0;
    Double totalInit = 0.0;
    LinearLayout progressBar;
    ImageView ivBack;
    ConstraintLayout placeOrderSuccess, placeOrder, clVoucherDiscount, clNotAddress, clAddressInfor;
    TextView tvChangeAddress, tvAddAddress, tvErrVoucher;
    TextView tvUserName, tvPhoneNumber, tvAddress;
    TextView tvTotalProductPrice, tvTotalShipPrice, tvTotalPrice, tvTotalVoucherDiscount;
    TextView tvPayOnDelivery, tvPayWithMomo;
    EditText edtNote, edtVoucher;
    AppCompatButton btnOrder, btnContinueShopping, btnVoucher;
    RadioButton rbPayOnDelivery, rbPayWithMomo;
    RecyclerView rcvProductCart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_out);
        initView();
        setOnClick();
        changeAddress();
        loadUserAddress();
        loadProductCart();
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        progressBar = findViewById(R.id.progressBar);
        placeOrderSuccess = findViewById(R.id.placeOrderSuccess);
        placeOrder = findViewById(R.id.placeOrder);
        tvUserName = findViewById(R.id.tvUserName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvTotalProductPrice = findViewById(R.id.tvTotalProductPrice);
        tvTotalShipPrice = findViewById(R.id.tvTotalShipPrice);
        tvTotalShipPrice.setText(formatPrice(shipPrice));
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        rcvProductCart = findViewById(R.id.rcvProductCart);
        btnOrder = findViewById(R.id.btnOrder);
        btnContinueShopping = findViewById(R.id.btnContinueShopping);
        rbPayOnDelivery = findViewById(R.id.rbPayOnDelivery);
        rbPayWithMomo = findViewById(R.id.rbPayWithMomo);
        tvPayOnDelivery = findViewById(R.id.tvPayOnDelivery);
        tvPayWithMomo = findViewById(R.id.tvPayWithMomo);
        edtNote = findViewById(R.id.edtNote);
        clVoucherDiscount = findViewById(R.id.clVoucherDiscount);
        tvTotalVoucherDiscount = findViewById(R.id.tvTotalVoucherDiscount);
        edtVoucher = findViewById(R.id.edtVoucher);
        tvErrVoucher = findViewById(R.id.tvErrVoucher);
        btnVoucher = findViewById(R.id.btnVoucher);
        clAddressInfor = findViewById(R.id.clAddressInfor);
        clNotAddress = findViewById(R.id.clNotAddress);
        tvChangeAddress = findViewById(R.id.tvChangeAddress);
        tvAddAddress = findViewById(R.id.tvAddAddress);
    }

    private void setOnClick() {
        btnOrder.setOnClickListener(this);
        tvPayOnDelivery.setOnClickListener(this);
        tvPayWithMomo.setOnClickListener(this);
        btnContinueShopping.setOnClickListener(this);
        btnVoucher.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvChangeAddress.setOnClickListener(this);
        tvAddAddress.setOnClickListener(this);
    }

    private void changeAddress() {
        Intent intent = getIntent();
        changeAdd = intent.getIntExtra("changeAdd", -1);
        createAdd = intent.getIntExtra("createAdd", -1);
        addressDefault = (UserAdressResponse) intent.getSerializableExtra("addressResponse");
        if (addressDefault != null && changeAdd == 1) {
            tvChangeAddress.setText("Thay đổi");
            tvUserName.setText(addressDefault.getFullname());
            tvPhoneNumber.setText("| " + addressDefault.getPhone());
            tvAddress.setText(addressDefault.getStreetName() + ", "
                    + addressDefault.getWards().getName()
                    + ", " + addressDefault.getWards().getDistricts().getName()
                    + ", " + addressDefault.getWards().getDistricts().getProvince().getName());
        }
    }

    private String formatPrice(Double totalPrice) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(totalPrice);
        return formattedPrice;
    }

    private void updateTotalPrice() {
        List<ProductCart> productCartList = CartManager.getCart(CheckOutActivity.this);
        Double total = 0.0;
        for (ProductCart cartProduct : productCartList) {
            total += cartProduct.getQuantity() * cartProduct.getProduct().getPrice();
        }
        tvTotalProductPrice.setText(formatPrice(total));
        tvTotalPrice.setText(formatPrice(total + shipPrice - discount));
        totalInit = total;
    }

    private void loadProductCart() {
        List<ProductCart> productCartList = CartManager.getCart(CheckOutActivity.this);
        for (ProductCart cartProduct : productCartList) {
            totalInit += cartProduct.getQuantity() * cartProduct.getProduct().getPrice();
        }
        tvTotalProductPrice.setText(formatPrice(totalInit));
        tvTotalPrice.setText(formatPrice(totalInit + shipPrice));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckOutActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvProductCart.setLayoutManager(linearLayoutManager);
        cartAdapter = new CartAdapter(productCartList, CheckOutActivity.this);
        rcvProductCart.setAdapter(cartAdapter);
        cartAdapter.setQuantityChangeListener(new CartAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChange(int position, int quantity) {
                updateTotalPrice();
            }
        });
    }

    private void loadUserAddress() {
        TokenDto user = UserManager.getSavedUser(CheckOutActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        UserAddressService userAddressService = ApiUtils.getUserAddressAPIService();
        Call<List<UserAdressResponse>> call = userAddressService.getListAddress("Bearer " + token, 0, 10);
        call.enqueue(new Callback<List<UserAdressResponse>>() {
            @Override
            public void onResponse(Call<List<UserAdressResponse>> call, Response<List<UserAdressResponse>> response) {
                if (response.isSuccessful()) {
                    List<UserAdressResponse> userAdressResponses = response.body();
                    if (changeAdd == -1) {
                        if (userAdressResponses.size() == 0) {
                            clNotAddress.setVisibility(View.VISIBLE);
                            clAddressInfor.setVisibility(View.GONE);
                        } else {
                            tvChangeAddress.setText("Thay đổi");
                            for (UserAdressResponse userAdressItem : userAdressResponses) {
                                if (userAdressItem.getPrimaryAddres()) {
                                    addressDefault = userAdressItem;
                                    break;
                                }
                                {
                                    addressDefault = userAdressResponses.get(0);
                                }
                            }
                            tvUserName.setText(addressDefault.getFullname());
                            tvPhoneNumber.setText("| " + addressDefault.getPhone());
                            tvAddress.setText(addressDefault.getStreetName() + ", "
                                    + addressDefault.getWards().getName()
                                    + ", " + addressDefault.getWards().getDistricts().getName()
                                    + ", " + addressDefault.getWards().getDistricts().getProvince().getName());
                        }
                    }

                    if (createAdd == 1) {
                        clAddressInfor.setVisibility(View.VISIBLE);
                        clNotAddress.setVisibility(View.GONE);
                        tvChangeAddress.setText("Thay đổi");
                        addressDefault = userAdressResponses.get(userAdressResponses.size() - 1);
                        tvUserName.setText(addressDefault.getFullname());
                        tvPhoneNumber.setText("| " + addressDefault.getPhone());
                        tvAddress.setText(addressDefault.getStreetName() + ", "
                                + addressDefault.getWards().getName()
                                + ", " + addressDefault.getWards().getDistricts().getName()
                                + ", " + addressDefault.getWards().getDistricts().getProvince().getName());
                    }
                } else {
                    Toast.makeText(CheckOutActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserAdressResponse>> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkVoucher() {
        if (edtVoucher.getText().toString().trim().isEmpty()) {
            tvErrVoucher.setVisibility(View.VISIBLE);
            tvErrVoucher.setText("Bạn chưa nhập Voucher");
            edtVoucher.requestFocus();
        } else {
            voucher = edtVoucher.getText().toString();
            double amount = totalInit;
            VoucherService voucherService = ApiUtils.getVoucherAPIService();
            Call<Voucher> call = voucherService.checkVoucher(voucher, amount);
            call.enqueue(new Callback<Voucher>() {
                @Override
                public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                    if (response.isSuccessful()) {
                        Voucher voucherResponse = response.body();
                        voucher = voucherResponse.getCode();
                        tvErrVoucher.setVisibility(View.GONE);
                        clVoucherDiscount.setVisibility(View.VISIBLE);
                        discount = voucherResponse.getDiscount();
                        tvTotalVoucherDiscount.setText("-" + formatPrice(discount));
                        tvTotalPrice.setText(formatPrice(totalInit + shipPrice - discount));
                    } else {
                        voucher = null;
                        ErrorResponse errorResponse = null;
                        try {
                            errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (errorResponse != null) {
                            String defaultMessage = errorResponse.getDefaultMessage();
                            if (defaultMessage.contains("Số tiền đơn hàng chưa đủ")) {
                                Pattern pattern = Pattern.compile("(\\d+\\.\\d+)");
                                Matcher matcher = pattern.matcher(defaultMessage);
                                if (matcher.find()) {
                                    double number = Double.parseDouble(matcher.group(1));
                                    Log.d("Number", "Số được tách ra từ chuỗi là: " + number);
                                    tvErrVoucher.setVisibility(View.VISIBLE);
                                    tvErrVoucher.setText("Số tiền đơn hàng chưa đủ, hãy mua thêm "
                                            + formatPrice(number)
                                            + " để được áp dụng voucher");
                                }
                            } else {
                                tvErrVoucher.setVisibility(View.VISIBLE);
                                tvErrVoucher.setText(defaultMessage);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Voucher> call, Throwable t) {

                }
            });
        }
    }

    private void createOrderCod() {
        InvoiceRequest invoiceRequest;
        PayType payType = PayType.PAYMENT_DELIVERY;
        Long userAddressId = addressDefault.getId();
        String note = edtNote.getText().toString();
        List<ProductSizeRequest> listSize = new ArrayList<>();
        List<ProductCart> productCartList = CartManager.getCart(CheckOutActivity.this);
        for (ProductCart cartProduct : productCartList) {
            Long idSize = cartProduct.getSize().getId();
            int quantity = cartProduct.getQuantity();
            listSize.add(new ProductSizeRequest(idSize, quantity));
        }

        invoiceRequest = new InvoiceRequest(payType, userAddressId, voucher, note, listSize);
        TokenDto user = UserManager.getSavedUser(CheckOutActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        InvoiceService invoiceService = ApiUtils.getInvoiceAPIService();
        Call<InvoiceResponse> call = invoiceService.creatInvoice("Bearer " + token, invoiceRequest);
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.isSuccessful()) {
                    placeOrder.setVisibility(View.GONE);
                    placeOrderSuccess.setVisibility(View.VISIBLE);

                    CartManager.clearCart(CheckOutActivity.this); // Xóa dữ liệu trong giỏ hàng
                    ToastPerfect.makeText(CheckOutActivity.this, ToastPerfect.SUCCESS, "Đặt hàng thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                } else {
                    ToastPerfect.makeText(CheckOutActivity.this, ToastPerfect.ERROR, "Đặt hàng không thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Log.e("checkout", t.getMessage());
            }
        });
    }

    private void requestPaymentMomo() {
        String content = "Thanh toán đơn hàng GOLDIE";
        String returnurl = "http://fithougoldie.com";

        List<ProductSizeRequest> listSize = new ArrayList<>();
        List<ProductCart> productCartList = CartManager.getCart(CheckOutActivity.this);
        for (ProductCart cartProduct : productCartList) {
            Long idSize = cartProduct.getSize().getId();
            int quantity = cartProduct.getQuantity();
            listSize.add(new ProductSizeRequest(idSize, quantity));
        }

        PaymentDto paymentDto = new PaymentDto(voucher, content, returnurl, returnurl, listSize);

        TokenDto user = UserManager.getSavedUser(CheckOutActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        MoMoService momoService = ApiUtils.getMomoAPIService();
        Call<ResponsePayment> call = momoService.requestPaymentMomo("Bearer " + token, paymentDto);
        call.enqueue(new Callback<ResponsePayment>() {
            @Override
            public void onResponse(Call<ResponsePayment> call, Response<ResponsePayment> response) {
                if (response.isSuccessful()) {
                    String url = response.body().getUrl();
                    requestIdMomo = response.body().getRequestId();
                    orderIdMomo = response.body().getOrderId();
                    Intent intent = new Intent(CheckOutActivity.this, MoMoActivity.class);
                    intent.putExtra("urlWeb", url);
//                    startActivity(intent);
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    startActivity(i);
                    activityResultLauncher.launch(intent);
                    Log.e("checkoutmomo", "success");
                } else {
                    Log.e("checkoutmomo", "fail");
                }
            }

            @Override
            public void onFailure(Call<ResponsePayment> call, Throwable t) {
                Log.e("checkoutmomo", t.getMessage());
            }
        });
    }

    private void createOrderMomo() {
        progressBar.setVisibility(View.VISIBLE);
        placeOrder.setVisibility(View.GONE);

        InvoiceRequest invoiceRequest;
        PayType payType = PayType.PAYMENT_MOMO;
        Long userAddressId = addressDefault.getId();
        String note = edtNote.getText().toString();
        List<ProductSizeRequest> listSize = new ArrayList<>();
        List<ProductCart> productCartList = CartManager.getCart(CheckOutActivity.this);
        for (ProductCart cartProduct : productCartList) {
            Long idSize = cartProduct.getSize().getId();
            int quantity = cartProduct.getQuantity();
            listSize.add(new ProductSizeRequest(idSize, quantity));
        }
        invoiceRequest = new InvoiceRequest(payType, requestIdMomo, orderIdMomo, userAddressId, voucher, note, listSize);

        TokenDto user = UserManager.getSavedUser(CheckOutActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        InvoiceService invoiceService = ApiUtils.getInvoiceAPIService();
        Call<InvoiceResponse> call = invoiceService.creatInvoice("Bearer " + token, invoiceRequest);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                call.enqueue(new Callback<InvoiceResponse>() {
                    @Override
                    public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            placeOrderSuccess.setVisibility(View.VISIBLE);
                            CartManager.clearCart(CheckOutActivity.this); // Xóa dữ liệu trong giỏ hàng
                            ToastPerfect.makeText(CheckOutActivity.this, ToastPerfect.SUCCESS, "Đặt hàng thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                        } else {
                            ToastPerfect.makeText(CheckOutActivity.this, ToastPerfect.ERROR, "Đặt hàng không thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                        Log.e("checkout", t.getMessage());
                    }
                });
            }
        }, 500);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;

            case R.id.tvPayOnDelivery:
                rbPayOnDelivery.setChecked(true);
                break;

            case R.id.tvPayWithMomo:
                rbPayWithMomo.setChecked(true);
                break;

            case R.id.btnVoucher:
                checkVoucher();
                break;

            case R.id.btnContinueShopping:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.tvChangeAddress:
                intent = new Intent(CheckOutActivity.this, MyAddressActivity.class);
                intent.putExtra("changeAdd", 1);
                startActivity(intent);
                finish();
                break;

            case R.id.tvAddAddress:
                intent = new Intent(CheckOutActivity.this, AddressDetailActivity.class);
                intent.putExtra("createAddShip", 1);
                startActivity(intent);
                finish();
                break;

            case R.id.btnOrder:
                if (!rbPayOnDelivery.isChecked() && !rbPayWithMomo.isChecked()) {
                    ToastPerfect.makeText(this, ToastPerfect.ERROR, "Bạn chưa chọn phương thức thanh toán", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                } else if (rbPayOnDelivery.isChecked()) {
                    createOrderCod();
                } else {
                    requestPaymentMomo();
                }
                break;

            default:
                break;
        }
    }
}