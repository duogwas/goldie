package fithou.duogwas.goldie.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import fithou.duogwas.goldie.Activity.ProductActivity;
import fithou.duogwas.goldie.Activity.ProductDetailActivity;
import fithou.duogwas.goldie.Adapter.CartAdapter;
import fithou.duogwas.goldie.Adapter.ProductColorAdapter;
import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Utils.CartManager;
import fithou.duogwas.goldie.Utils.ObjectSharedPreferences;


public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    ProductCart productCart;
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
        AnhXa();
    }

    private void AnhXa() {
        recyclerView=getView().findViewById(R.id.rcvCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<ProductCart> cartProducts = CartManager.getCart(getContext());
        cartAdapter= new CartAdapter(cartProducts,getContext());
        recyclerView.setAdapter(cartAdapter);
    }

}