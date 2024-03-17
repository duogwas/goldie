package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import fithou.duogwas.goldie.Utils.CartManager;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<ProductCart> productCart;
    Context context;

    public CartAdapter(List<ProductCart> productCart, Context context) {
        this.productCart = productCart;
        this.context = context;
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(int position, int quantity);
    }

    private OnQuantityChangeListener quantityChangeListener;

    public void setQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
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

        Glide.with(context)
                .load(cart.getProduct().getImageBanner())
                .into(holder.ivImage);
        holder.tvName.setText(cart.getProduct().getName());
        holder.tvOption.setText("Màu " + cart.getColor().getColorName() + ", Size " + cart.getSize().getSizeName());
        holder.tvCount.setText(String.valueOf(cart.getQuantity()));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(cart.getProduct().getPrice());
        holder.tvTotalPrice.setText(formattedPrice);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "id: " + cart.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart.getQuantity() >= cart.getSize().getQuantity()) {
                    Toast.makeText(context, "max rồi", Toast.LENGTH_SHORT).show();
                    return;
                }
                cart.setQuantity(cart.getQuantity() + 1);
                holder.tvCount.setText(String.valueOf(cart.getQuantity()));
                productCart.set(position, cart);
                CartManager.saveCart(context, productCart);
                notifyDataSetChanged();
                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChange(position, cart.getQuantity());
                }
            }
        });

        holder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart.getQuantity() <= 1) {
                    Toast.makeText(context, "min rồi", Toast.LENGTH_SHORT).show();
                    return;
                }
                cart.setQuantity(cart.getQuantity() - 1);
                holder.tvCount.setText(String.valueOf(cart.getQuantity()));
                productCart.set(position, cart);
                CartManager.saveCart(context, productCart);
                notifyDataSetChanged();
                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChange(position, cart.getQuantity());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage, ivPlus, ivMinus;;
        TextView tvName, tvOption, tvCount, tvTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivPlus=itemView.findViewById(R.id.ivPlus);
            ivMinus=itemView.findViewById(R.id.ivMinus);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvOption = itemView.findViewById(R.id.tvOption);
            tvCount = itemView.findViewById(R.id.tvCount);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}
