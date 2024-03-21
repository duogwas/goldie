package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.Adapter.CartAdapter;
import fithou.duogwas.goldie.Adapter.UserAddressAdapter;
import fithou.duogwas.goldie.Entity.PayType;
import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.Fragment.HomeFragment;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Request.InvoiceRequest;
import fithou.duogwas.goldie.Request.ProductSizeRequest;
import fithou.duogwas.goldie.Response.InvoiceResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Response.UserAdressResponse;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.InvoiceService;
import fithou.duogwas.goldie.Retrofit.UserAddressService;
import fithou.duogwas.goldie.Utils.CartManager;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {
    UserAdressResponse addressDefault = null;
    Double shipPrice = 20000.0;
    Double totalInit = 0.0;
    ConstraintLayout placeOrderSuccess, placeOrder;
    TextView tvUserName, tvPhoneNumber, tvAddress;
    TextView tvTotalProductPrice, tvTotalShipPrice, tvTotalPrice;
    TextView tvPayOnDelivery, tvPayWithMomo;
    EditText edtNote;
    AppCompatButton btnOrder, btnContinueShopping;
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
        loadProductCart();
        loadUserAddress();
    }

    private void initView() {
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
    }

    private void setOnClick() {
        btnOrder.setOnClickListener(this);
        tvPayOnDelivery.setOnClickListener(this);
        tvPayWithMomo.setOnClickListener(this);
        btnContinueShopping.setOnClickListener(this);
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
        tvTotalPrice.setText(formatPrice(total + shipPrice));
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
                    for (UserAdressResponse userAdressItem : userAdressResponses) {
                        if (userAdressItem.getPrimaryAddres()) {
                            addressDefault = userAdressItem;
                            break;
                        }
                    }
                    tvUserName.setText(addressDefault.getFullname());
                    tvPhoneNumber.setText(addressDefault.getPhone());
                    tvAddress.setText(addressDefault.getStreetName() + ", "
                            + addressDefault.getWards().getName()
                            + ", " + addressDefault.getWards().getDistricts().getName()
                            + ", " + addressDefault.getWards().getDistricts().getProvince().getName());
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

    private void createOrderCod() {
        InvoiceRequest invoiceRequest;
        PayType payType = PayType.PAYMENT_DELIVERY;
        Long userAddressId = addressDefault.getId();
        String voucherCode = "";
        String note = edtNote.getText().toString();
        List<ProductSizeRequest> listSize = new ArrayList<>();
        List<ProductCart> productCartList = CartManager.getCart(CheckOutActivity.this);
        for (ProductCart cartProduct : productCartList) {
            Long idSize = cartProduct.getSize().getId();
            int quantity = cartProduct.getQuantity();
            listSize.add(new ProductSizeRequest(idSize, quantity));
        }

        if (edtNote.getText().toString().trim().isEmpty()) {
            note = "";
        }

        invoiceRequest = new InvoiceRequest(payType, userAddressId, voucherCode, note, listSize);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvPayOnDelivery:
                rbPayOnDelivery.setChecked(true);
                break;

            case R.id.tvPayWithMomo:
                rbPayWithMomo.setChecked(true);
                break;

            case R.id.btnContinueShopping:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.btnOrder:
                if (!rbPayOnDelivery.isChecked() && !rbPayWithMomo.isChecked()) {
                    // Cả hai RadioButton đều không được chọn
                    ToastPerfect.makeText(this, ToastPerfect.ERROR, "Bạn chưa chọn phương thức thanh toán", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                } else if (rbPayOnDelivery.isChecked()) {
                    createOrderCod();
                } else {
                    ToastPerfect.makeText(this, ToastPerfect.INFORMATION, "Phương thức thanh toán Momo đang được phát triển", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }

    }
}