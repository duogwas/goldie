package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.Activity.ProductDetailActivity;
import fithou.duogwas.goldie.Activity.SignInActivity;
import fithou.duogwas.goldie.Entity.Category;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import fithou.duogwas.goldie.Response.ProductResponse;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<ProductResponse> product;
    Context context;

    public ProductAdapter(List<ProductResponse> productResponses, Context context) {
        this.product = productResponses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductResponse productResponse = product.get(position);
        if (productResponse == null) {
            return;
        }

        holder.tvProductName.setText(productResponse.getName());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(productResponse.getPrice());
        holder.tvProductPrice.setText(formattedPrice);
        holder.tvQuantitySold.setText("Đã bán " + String.valueOf(productResponse.getQuantitySold()));
        Glide.with(context)
                .load(productResponse.getImageBanner())
                .into(holder.imgProductImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("idProduct", productResponse.getId());
                intent.putExtra("priceProduct",productResponse.getPrice());
                intent.putExtra("productResponse", productResponse);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imgProductImage;
        TextView tvQuantitySold;
        TextView tvProductName;
        TextView tvProductPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductImage = itemView.findViewById(R.id.imgProductImage);
            tvQuantitySold = itemView.findViewById(R.id.tvQuantitySold);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
