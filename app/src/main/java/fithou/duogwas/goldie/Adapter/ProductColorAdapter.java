package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 05/03/2024.
//


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fithou.duogwas.goldie.Activity.ProductDetailActivity;
import fithou.duogwas.goldie.Entity.ProductColor;
import fithou.duogwas.goldie.Entity.ProductSize;
import fithou.duogwas.goldie.R;


public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.ViewHolder> {
    List<ProductColor> productColor;
    Context context;
    TextView tvColorName, tvSizeName;
    ConstraintLayout clSize;
    private int selectedItemPosition = -1;


    public ProductColorAdapter(List<ProductColor> productColor, Context context, TextView tvColorName, ConstraintLayout clSize, TextView tvSizeName) {
        this.productColor = productColor;
        this.context = context;
        this.tvColorName = tvColorName;
        this.clSize = clSize;
        this.tvSizeName = tvSizeName;
    }

    public interface OnColorClickListener {
        void onColorClick(Long idColor);
    }

    // Trong adapter của bạn, khởi tạo một instance của interface này
    private OnColorClickListener onColorClickListener;

    // Phương thức để set listener
    public void setOnColorClickListener(OnColorClickListener listener) {
        this.onColorClickListener = listener;
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

        if (selectedItemPosition == position) {
            holder.clAvt.setBackgroundResource(R.drawable.bg_item_size_select);
        } else {
            holder.clAvt.setBackgroundResource(R.drawable.bg_item_category);
        }

        Glide.with(context)
                .load(color.getLinkImage())
                .into(holder.imgColorPic);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItemPosition = position;
                notifyDataSetChanged();
                clSize.setVisibility(View.VISIBLE);
                tvSizeName.setText("Vui lòng chọn kích thước sản phẩm");
                Long idColor = color.getId();
                tvColorName.setText(color.getColorName());
                if (onColorClickListener != null) {
                    onColorClickListener.onColorClick(idColor);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return productColor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgColorPic;
        ConstraintLayout clAvt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgColorPic = itemView.findViewById(R.id.imgColorPic);
            clAvt = itemView.findViewById(R.id.clAvt);
        }
    }
}
