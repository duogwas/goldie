package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.Activity.ProductDetailActivity;
import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.ProductResponse;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<ProductCart> productCart;
    Context context;

    public CartAdapter(List<ProductCart> productCart, Context context) {
        this.productCart = productCart;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCart cart = productCart.get(position);
        if (cart == null) {
            return;
        }

        holder.pnam.setText(cart.getProduct().getName());
        holder.pquantity.setText(String.valueOf(cart.getQuantity()));
        holder.psz.setText(cart.getSize().getSizeName());
        holder.pcl.setText(cart.getColor().getColorName());
    }

    @Override
    public int getItemCount() {
        return productCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pnam;
        TextView pquantity;
        TextView psz;
        TextView pcl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pnam = itemView.findViewById(R.id.pnam);
            pquantity = itemView.findViewById(R.id.pquantity);
            psz = itemView.findViewById(R.id.psz);
            pcl = itemView.findViewById(R.id.pcl);
        }
    }
}
