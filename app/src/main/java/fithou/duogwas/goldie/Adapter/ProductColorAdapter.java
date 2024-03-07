package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 05/03/2024.
//


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fithou.duogwas.goldie.Entity.ProductColor;
import fithou.duogwas.goldie.Entity.ProductSize;
import fithou.duogwas.goldie.R;


public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.ViewHolder>{
    List<ProductColor> productColor;
    Context context;
    ProductSizeAdapter sizeAdapter;
    RecyclerView rcvSize;

    public ProductColorAdapter(List<ProductColor> productColor, Context context, ProductSizeAdapter sizeAdapter, RecyclerView rcvSize) {
        this.productColor = productColor;
        this.context = context;
        this.sizeAdapter = sizeAdapter;
        this.rcvSize = rcvSize;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_color, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductColor color = productColor.get(position);
        if (productColor == null) {
            return;
        }

        Glide.with(context)
                .load(color.getLinkImage())
                .into(holder.imgColorPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ProductSize> size = color.getProductSizes();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                rcvSize.setLayoutManager(linearLayoutManager);
                sizeAdapter = new ProductSizeAdapter(size, context);
                rcvSize.setAdapter(sizeAdapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productColor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgColorPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgColorPic = itemView.findViewById(R.id.imgColorPic);
        }
    }
}
