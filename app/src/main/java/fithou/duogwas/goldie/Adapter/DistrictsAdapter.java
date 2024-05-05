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
import fithou.duogwas.goldie.Entity.Province;
import fithou.duogwas.goldie.R;

public class DistrictsAdapter extends ArrayAdapter<Districts> {
    private List<Districts> districtsList;
    private LayoutInflater inflater;

    public DistrictsAdapter (Context context, int resource, List<Districts> districtsList) {
        super(context, resource, districtsList);
        this.districtsList = districtsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        TextView tvNameAddress = view.findViewById(R.id.tvNameAddress);
        tvNameAddress.setText(districtsList.get(position).getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        TextView tvNameAddress = view.findViewById(R.id.tvNameAddress);
        tvNameAddress.setText(districtsList.get(position).getName());
        return view;
    }

    @Nullable
    @Override
    public Districts getItem(int position) {
        return districtsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return districtsList.get(position).getId();
    }
}
