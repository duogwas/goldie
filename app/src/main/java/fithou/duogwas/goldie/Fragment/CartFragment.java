package fithou.duogwas.goldie.Fragment;

import android.opengl.Visibility;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.Adapter.CartAdapter;
import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Utils.CartManager;


public class CartFragment extends Fragment {

    Double totalInit = 0.0;
    ConstraintLayout clCartIsEmpty, clBtnCart;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    TextView tvTotalPrice;
    AppCompatButton btnCheckout;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        loadAllCart();
    }

    private void initView() {
        clCartIsEmpty = getView().findViewById(R.id.clCartIsEmpty);
        clBtnCart=getView().findViewById(R.id.clBtnCart);
        tvTotalPrice = getView().findViewById(R.id.tvTotalPrice);
        btnCheckout = getView().findViewById(R.id.btnCheckout);
    }

    private void loadAllCart() {
        recyclerView = getView().findViewById(R.id.rcvCart);
        List<ProductCart> productCartList = CartManager.getCart(getContext());
        if (productCartList.size() == 0) {
            clCartIsEmpty.setVisibility(View.VISIBLE);
            clBtnCart.setVisibility(View.GONE);
        } else {
            for (ProductCart cartProduct : productCartList) {
                totalInit += cartProduct.getQuantity() * cartProduct.getProduct().getPrice();
            }
            tvTotalPrice.setText(formatPrice(totalInit));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            cartAdapter = new CartAdapter(productCartList, getContext());
            recyclerView.setAdapter(cartAdapter);
            cartAdapter.setQuantityChangeListener(new CartAdapter.OnQuantityChangeListener() {
                @Override
                public void onQuantityChange(int position, int quantity) {
                    updateTotalPrice();
                }
            });

        }
    }


    private String formatPrice(Double totalPrice) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(totalPrice);
        return formattedPrice;
    }

    private void updateTotalPrice() {
        List<ProductCart> productCartList = CartManager.getCart(getContext());
        Double total = 0.0;
        for (ProductCart cartProduct : productCartList) {
            total += cartProduct.getQuantity() * cartProduct.getProduct().getPrice();
        }
        tvTotalPrice.setText(formatPrice(total));
    }

}