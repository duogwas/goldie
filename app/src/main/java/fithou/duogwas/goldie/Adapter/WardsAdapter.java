package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 19/03/2024.
//


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fithou.duogwas.goldie.Entity.Districts;
import fithou.duogwas.goldie.Entity.Wards;
import fithou.duogwas.goldie.R;

public class WardsAdapter extends ArrayAdapter<Wards> {
    private List<Wards> wardsList;
    private LayoutInflater inflater;

    public WardsAdapter(Context context, int resource, List<Wards> wardsList) {
        super(context, resource, wardsList);
        this.wardsList = wardsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        TextView tvNameAddress = view.findViewById(R.id.tvNameAddress);
        tvNameAddress.setText(wardsList.get(position).getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        TextView tvNameAddress = view.findViewById(R.id.tvNameAddress);
        tvNameAddress.setText(wardsList.get(position).getName());
        return view;
    }

    @Nullable
    @Override
    public Wards getItem(int position) {
        return wardsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return wardsList.get(position).getId();
    }
}
