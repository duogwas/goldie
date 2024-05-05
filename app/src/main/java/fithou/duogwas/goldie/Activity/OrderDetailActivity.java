package fithou.duogwas.goldie.Activity;

import static fithou.duogwas.goldie.Entity.PayType.PAYMENT_DELIVERY;
import static fithou.duogwas.goldie.Entity.PayType.PAYMENT_MOMO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.Adapter.CartAdapter;
import fithou.duogwas.goldie.Adapter.ProductInvoiceDetailAdapter;
import fithou.duogwas.goldie.Entity.InvoiceStatus;
import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.InvoiceDetailResponse;
import fithou.duogwas.goldie.Response.InvoiceResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.InvoiceService;
import fithou.duogwas.goldie.Utils.CartManager;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class OrderDetailActivity extends AppCompatActivity {
    ImageView ivBack;
    TextView tvOrderId, tvStatus, tvStatusDate, tvAddress, tvCountItem, tvPaymentMethod;
    TextView tvTotalProductPrice, tvShipPrice, tvDiscount, tvTotalPrice;
    TextView tvTimeOrder, tvTimeShip, tvTimeComplete, tvTimeCancel;
    ConstraintLayout clVoucher, clTimeShip, clTimeComplete, clTimeCancel;
    RecyclerView rcvProductCart;
    AppCompatButton btnCancel;
    List<InvoiceDetailResponse> detailResponseList = new ArrayList<>();
    Long idInvoice;
    Long idToPay = Long.valueOf(1);
    Long idToReceive = Long.valueOf(3);
    Long idCompleted = Long.valueOf(4);
    Long idCancelled = Long.valueOf(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_detail);
        initView();
        getIdInvoice();
        loadInvoice(idInvoice);
        loadProductInvoice(idInvoice);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete();
            }
        });
    }

    private void initView() {
        btnCancel = findViewById(R.id.btnCancel);
        ivBack = findViewById(R.id.ivBack);
        tvOrderId = findViewById(R.id.tvOrderId);
        tvStatus = findViewById(R.id.tvStatus);
        tvStatusDate = findViewById(R.id.tvStatusDate);
        tvAddress = findViewById(R.id.tvAddress);
        tvCountItem = findViewById(R.id.tvCountItem);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);
        tvTotalProductPrice = findViewById(R.id.tvTotalProductPrice);
        tvShipPrice = findViewById(R.id.tvShipPrice);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvTimeOrder = findViewById(R.id.tvTimeOrder);
        tvTimeShip = findViewById(R.id.tvTimeShip);
        tvTimeComplete = findViewById(R.id.tvTimeComplete);
        tvTimeCancel = findViewById(R.id.tvTimeCancel);
        clVoucher = findViewById(R.id.clVoucher);
        clTimeShip = findViewById(R.id.clTimeShip);
        clTimeComplete = findViewById(R.id.clTimeComplete);
        clTimeCancel = findViewById(R.id.clTimeCancel);
        rcvProductCart = findViewById(R.id.rcvProductCart);
    }

    private void getIdInvoice() {
        Intent intent = getIntent();
        idInvoice = intent.getLongExtra("idInvoice", -1);
    }

    private String formatPrice(Double totalPrice) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(totalPrice);
        return formattedPrice;
    }

    private void loadInvoice(Long id) {
        TokenDto user = UserManager.getSavedUser(OrderDetailActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        InvoiceService invoiceService = ApiUtils.getInvoiceAPIService();
        Call<InvoiceResponse> call = invoiceService.getInvoiceById("Bearer " + token, id);
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.isSuccessful()) {
                    InvoiceResponse invoiceResponse = response.body();

                    //trạng thái mới nhất của đơn hàng
                    List<InvoiceStatus> invoiceStatuses = invoiceResponse.getInvoiceStatuses();
                    tvStatus.setText(invoiceStatuses.get(invoiceStatuses.size() - 1).getStatus().getName());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    tvStatusDate.setText(formatter.format(invoiceStatuses.get(invoiceStatuses.size() - 1).getCreatedDate()));

                    //ẩn hiện nút hủy
                    if(invoiceStatuses.get(invoiceStatuses.size() - 1).getStatus().getId().equals(idToPay)
                            && invoiceResponse.getPayType() == PAYMENT_DELIVERY){
                        btnCancel.setVisibility(View.VISIBLE);
                    }

                    //địa chỉ nhận hàng
                    tvAddress.setText(invoiceResponse.getUserAddress().getFullname() + "\n"
                            + invoiceResponse.getUserAddress().getPhone() + "\n"
                            + invoiceResponse.getUserAddress().getStreetName() + ", "
                            + invoiceResponse.getUserAddress().getWards().getName() + ", "
                            + invoiceResponse.getUserAddress().getWards().getDistricts().getName() + ", "
                            + invoiceResponse.getUserAddress().getWards().getDistricts().getProvince().getName());

                    //thanh toán
                    tvTotalPrice.setText(formatPrice(invoiceResponse.getTotalAmount()));
                    tvShipPrice.setText(formatPrice(20000.0));

                    //mã đơn hàng
                    tvOrderId.setText("#Goldie." + String.valueOf(invoiceResponse.getId()));

                    //phương thức thanh toán
                    if (invoiceResponse.getPayType() == PAYMENT_MOMO) {
                        tvPaymentMethod.setText("Thanh toán MoMo");
                    } else {
                        tvPaymentMethod.setText("Thanh toán khi nhận hàng");
                    }

                    //mốc thời gian
                    //1. tạo đơn hàng
                    tvTimeOrder.setText(formatter.format(invoiceResponse.getCreatedDate()) + " " + invoiceResponse.getCreatedTime().substring(0, 5));

                    //2.gửi hàng cho ship
                    for (InvoiceStatus status : invoiceStatuses) {
                        if (status.getStatus().getId().equals(idToReceive)) {
                            clTimeShip.setVisibility(View.VISIBLE);
                            tvTimeShip.setText(formatter.format(status.getCreatedDate()) + " " + status.getCreatedTime().substring(0, 5));
                            break;
                        }
                    }

                    //3. nhận hàng
                    for (InvoiceStatus status : invoiceStatuses) {
                        if (status.getStatus().getId().equals(idCompleted)) {
                            clTimeComplete.setVisibility(View.VISIBLE);
                            tvTimeComplete.setText(formatter.format(status.getCreatedDate()) + " " + status.getCreatedTime().substring(0, 5));
                            break;
                        }
                    }

                    //4.hủy
                    for (InvoiceStatus status : invoiceStatuses) {
                        if (status.getStatus().getId().equals(idCancelled)) {
                            clTimeCancel.setVisibility(View.VISIBLE);
                            tvTimeCancel.setText(formatter.format(status.getCreatedDate()) + " " + status.getCreatedTime().substring(0, 5));
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {

            }
        });

    }

    private void loadProductInvoice(Long id) {
        TokenDto user = UserManager.getSavedUser(OrderDetailActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        InvoiceService invoiceService = ApiUtils.getInvoiceAPIService();
        Call<List<InvoiceDetailResponse>> call = invoiceService.getInvoiceDetail("Bearer " + token, id);
        call.enqueue(new Callback<List<InvoiceDetailResponse>>() {
            @Override
            public void onResponse(Call<List<InvoiceDetailResponse>> call, Response<List<InvoiceDetailResponse>> response) {
                if (response.isSuccessful()) {
                    detailResponseList = response.body();
                    //tổng tiền hàng
                    updateTotalPrice(detailResponseList);

                    //đếm số lượng sp
                    tvCountItem.setText(String.valueOf(detailResponseList.size()) + " sản phẩm");

                    //list product
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                    rcvProductCart.setLayoutManager(linearLayoutManager);
                    ProductInvoiceDetailAdapter adapter = new ProductInvoiceDetailAdapter(detailResponseList, OrderDetailActivity.this, clVoucher, tvDiscount);
                    rcvProductCart.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<InvoiceDetailResponse>> call, Throwable t) {

            }
        });

    }

    private void updateTotalPrice(List<InvoiceDetailResponse> list) {
        Double total = 0.0;
        for (InvoiceDetailResponse item : list) {
            total += item.getQuantity() * item.getPrice();
        }
        tvTotalProductPrice.setText(formatPrice(total));
    }

    private void cancelOrder(Long id) {
        TokenDto user = UserManager.getSavedUser(OrderDetailActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        InvoiceService invoiceService = ApiUtils.getInvoiceAPIService();
        Call<InvoiceResponse> call = invoiceService.cancelInvoice("Bearer " + token, id);
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(OrderDetailActivity.this, MyOrderActivity.class));
                    ToastPerfect.makeText(OrderDetailActivity.this, ToastPerfect.SUCCESS, "Hủy đơn hàng thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                } else {
                    Log.e("cancelOrder", "response not successful");
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Log.e("cancelOrder", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showDialogDelete() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cancel_order);
        AppCompatButton btnDelete = dialog.findViewById(R.id.btnDelete);
        AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancel);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder(idInvoice);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}