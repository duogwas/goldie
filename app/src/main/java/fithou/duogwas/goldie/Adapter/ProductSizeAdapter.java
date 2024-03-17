package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 05/03/2024.
//


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fithou.duogwas.goldie.Entity.ProductSize;
import fithou.duogwas.goldie.R;


public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.ViewHolder> {
    List<ProductSize> productSize;
    Context context;
    TextView tvSizeName;
    private int selectedItemPosition = -1;

    public ProductSizeAdapter(List<ProductSize> productSize, Context context, TextView tvSizeName) {
        this.productSize = productSize;
        this.context = context;
        this.tvSizeName = tvSizeName;
    }

    public interface OnSizeClickListener {
        void onSizeClick(Long idSize);
    }

    // Trong adapter của bạn, khởi tạo một instance của interface này
    private OnSizeClickListener onSizeClickListener;

    // Phương thức để set listener
    public void setOnSizeClickListener(OnSizeClickListener listener) {
        this.onSizeClickListener = listener;
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

        if (selectedItemPosition == position) {
            holder.tvSizeName.setBackgroundColor(Color.BLACK);
            holder.tvSizeName.setTextColor(Color.WHITE);
        } else {
            holder.tvSizeName.setBackgroundResource(R.drawable.bg_button);
            holder.tvSizeName.setTextColor(Color.BLACK);
        }

        if (size.getQuantity()==0) {
            holder.tvSizeName.setBackgroundResource(R.drawable.bg_size_sold_out);
            holder.tvSizeName.setTextColor(Color.WHITE);
            holder.tvSizeName.setEnabled(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItemPosition = position;
                notifyDataSetChanged();
                if(size.getQuantity()==0){
                    tvSizeName.setText("");
                }
                else {
                    tvSizeName.setText(size.getSizeName());
                }
                Long idSize = size.getId();
                if (onSizeClickListener != null) {
                    onSizeClickListener.onSizeClick(idSize);
                }
            }
        });
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
