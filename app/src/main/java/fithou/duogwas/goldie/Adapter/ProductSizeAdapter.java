package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 05/03/2024.
//


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fithou.duogwas.goldie.Entity.ProductSize;
import fithou.duogwas.goldie.R;


public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.ViewHolder> {
    List<ProductSize> productSize;
    Context context;

    public ProductSizeAdapter(List<ProductSize> productSize, Context context) {
        this.productSize = productSize;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_size, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductSize size = productSize.get(position);
        if (productSize == null) {
            return;
        }

        holder.tvSizeName.setText(size.getSizeName());
    }

    @Override
    public int getItemCount() {
        return productSize.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSizeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSizeName = itemView.findViewById(R.id.tvSizeName);
        }
    }
}
