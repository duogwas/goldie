package fithou.duogwas.goldie.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
import fithou.duogwas.goldie.Response.ProductResponse;
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

public class MoMoActivity extends AppCompatActivity implements View.OnClickListener {
    String urlWeb;
    ConstraintLayout placeOrderSuccess;
    AppCompatButton btnContinueShopping;
    WebView wvMomoPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_momo);
        initView();
        getUrlWebview();
        webViewSetting();
        setOnClick();


    }

    private void initView() {
        wvMomoPayment = findViewById(R.id.wvMomoPayment);
        placeOrderSuccess = findViewById(R.id.placeOrderSuccess);
        btnContinueShopping = findViewById(R.id.btnContinueShopping);
    }

    private void getUrlWebview() {
        Intent intent = getIntent();
        urlWeb = intent.getStringExtra("urlWeb");
        wvMomoPayment.loadUrl(urlWeb);
        wvMomoPayment.getSettings().setJavaScriptEnabled(true);
    }

    private void webViewSetting() {
        wvMomoPayment.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebView", "your current url when webpage loading.. finish" + url);
                if (url.contains("http://fithougoldie.com/")) {
                    Intent intent = new Intent(MoMoActivity.this, CheckOutActivity.class);
                    setResult(RESULT_OK);
                    finish();
                    wvMomoPayment.setVisibility(View.GONE);
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    private void setOnClick() {
        btnContinueShopping.setOnClickListener(this);
    }

    private void createOrderCod() {
//        InvoiceRequest invoiceRequest;
//        PayType payType = PayType.PAYMENT_DELIVERY;
//        Long userAddressId = addressDefault.getId();
//        String note = edtNote.getText().toString();
//        List<ProductSizeRequest> listSize = new ArrayList<>();
//        List<ProductCart> productCartList = CartManager.getCart(MoMoActivity.this);
//        for (ProductCart cartProduct : productCartList) {
//            Long idSize = cartProduct.getSize().getId();
//            int quantity = cartProduct.getQuantity();
//            listSize.add(new ProductSizeRequest(idSize, quantity));
//        }
//
//        invoiceRequest = new InvoiceRequest(payType, userAddressId, voucher, note, listSize);
//        TokenDto user = UserManager.getSavedUser(MoMoActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
//        String token = user.getToken();
//        InvoiceService invoiceService = ApiUtils.getInvoiceAPIService();
//        Call<InvoiceResponse> call = invoiceService.creatInvoice("Bearer " + token, invoiceRequest);
//        call.enqueue(new Callback<InvoiceResponse>() {
//            @Override
//            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
//                if (response.isSuccessful()) {
//                    placeOrder.setVisibility(View.GONE);
//                    placeOrderSuccess.setVisibility(View.VISIBLE);
//                    CartManager.clearCart(MoMoActivity.this); // Xóa dữ liệu trong giỏ hàng
//                    ToastPerfect.makeText(MoMoActivity.this, ToastPerfect.SUCCESS, "Đặt hàng thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
//                } else {
//                    ToastPerfect.makeText(MoMoActivity.this, ToastPerfect.ERROR, "Đặt hàng không thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
//                Log.e("checkout", t.getMessage());
//            }
//        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContinueShopping:
                startActivity(new Intent(this, MainActivity.class));
                break;

            default:
                break;
        }

    }
}