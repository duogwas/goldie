package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 18/03/2024.
//


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import fithou.duogwas.goldie.Activity.MyAddressDetailActivity;
import fithou.duogwas.goldie.Activity.ProductDetailActivity;
import fithou.duogwas.goldie.Activity.SignInActivity;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.UserAdressResponse;

public class UserAddressAdapter extends RecyclerView.Adapter<UserAddressAdapter.ViewHolder> {
    Context context;
    List<UserAdressResponse> userAddressResponses;

    public UserAddressAdapter(List<UserAdressResponse> userAddressResponse, Context context) {
        this.userAddressResponses = userAddressResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_address, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserAdressResponse response = userAddressResponses.get(position);

        holder.tvName.setText(response.getFullname());
        holder.tvPhone.setText(response.getPhone());
        holder.tvAddress.setText(response.getStreetName() + ", "
                + response.getWards().getName()
                + ", " + response.getWards().getDistricts().getName()
                + ", " + response.getWards().getDistricts().getProvince().getName());

        if (response.getPrimaryAddres()) {
            holder.clDefaultAddress.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyAddressDetailActivity.class);
                intent.putExtra("idAddress", response.getId());
                intent.putExtra("idProvince", response.getWards().getDistricts().getProvince().getId());
                intent.putExtra("idDistrict", response.getWards().getDistricts().getId());
                intent.putExtra("idWards", response.getWards().getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userAddressResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvAddress;
        ConstraintLayout clDefaultAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            clDefaultAddress = itemView.findViewById(R.id.clDefaultAddress);
        }
    }
}
