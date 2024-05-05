package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 27/03/2024.
//


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.InvoiceDetailResponse;

public class ProductInvoiceDetailAdapter extends RecyclerView.Adapter<ProductInvoiceDetailAdapter.ViewHolder> {

    Context context;
    List<InvoiceDetailResponse> detailResponseList;

    ConstraintLayout clVoucher;
    TextView tvDiscount;

    public ProductInvoiceDetailAdapter(List<InvoiceDetailResponse> detailResponseList, Context context, ConstraintLayout clVoucher, TextView tvDiscount) {
        this.detailResponseList = detailResponseList;
        this.context = context;
        this.clVoucher = clVoucher;
        this.tvDiscount = tvDiscount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_invoice_detail, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InvoiceDetailResponse response = detailResponseList.get(position);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        if (response.getInvoice().getVoucher() != null) {
            clVoucher.setVisibility(View.VISIBLE);
            tvDiscount.setText("-" + currencyFormat.format(response.getInvoice().getVoucher().getDiscount()));
        }
        Glide.with(context)
                .load(response.getProduct().getImageBanner())
                .into(holder.ivProductImage);
        holder.tvProductName.setText(response.getProductName());
        holder.tvOption.setText(response.getColorName() + ", " + response.getProductSize().getSizeName());
        holder.tvQuantity.setText("x" + String.valueOf(response.getQuantity()));
        String formattedPrice = currencyFormat.format(response.getPrice());
        holder.tvPrice.setText(formattedPrice);
    }

    @Override
    public int getItemCount() {
        return detailResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName, tvOption, tvQuantity, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOption = itemView.findViewById(R.id.tvOption);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
