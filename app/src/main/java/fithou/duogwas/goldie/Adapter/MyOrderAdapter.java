package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 18/03/2024.
//


import static fithou.duogwas.goldie.Entity.PayType.PAYMENT_MOMO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.InvoiceResponse;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    Context context;
    List<InvoiceResponse> invoiceResponses;

    public MyOrderAdapter(List<InvoiceResponse> invoiceResponses, Context context) {
        this.invoiceResponses = invoiceResponses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_order, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InvoiceResponse response = invoiceResponses.get(position);

        holder.tvOderId.setText("#Goldie." + String.valueOf(response.getId()));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvPurchaseDay.setText(response.getCreatedTime() + " | " + formatter.format(response.getCreatedDate()));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(response.getTotalAmount());
        holder.tvTotalPrice.setText(formattedPrice);
        holder.tvStatus.setText(response.getStatus().getName());
        if (response.getPayType() == PAYMENT_MOMO) {
            holder.tvPayType.setText("Thanh toán MoMo");
        } else {
            holder.tvPayType.setText("Thanh toán COD");
        }

        holder.btnOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.valueOf(response.getId()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoiceResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOderId, tvPurchaseDay, tvTotalPrice, tvPayType, tvStatus;
        AppCompatButton btnOrderDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOderId = itemView.findViewById(R.id.tvOderId);
            tvPurchaseDay = itemView.findViewById(R.id.tvPurchaseDay);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvPayType = itemView.findViewById(R.id.tvPayType);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnOrderDetail = itemView.findViewById(R.id.btnOrderDetail);
        }
    }
}
