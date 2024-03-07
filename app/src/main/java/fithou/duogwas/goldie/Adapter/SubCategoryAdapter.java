package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fithou.duogwas.goldie.Entity.Category;
import fithou.duogwas.goldie.R;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    List<Category> categories;
    Context context;

    public SubCategoryAdapter(List<Category> subCategories, Context context) {
        this.categories = subCategories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category subCategories = categories.get(position);
        if (subCategories == null) {
            return;
        }

        holder.categoryName.setText(subCategories.getName());
        Glide.with(context)
                .load(subCategories.getImageBanner())
                .into(holder.categoryPic);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName, tv2;
        ImageView categoryPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tvSubCategoryName);
            categoryPic = itemView.findViewById(R.id.imgSubCategoryPic);
        }
    }
}
