package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fithou.duogwas.goldie.Model.Category;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import vn.thanguit.toastperfect.ToastPerfect;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastPerfect.makeText(context, ToastPerfect.INFORMATION, "Bạn đã chọn: " + subCategories.getName(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        CircleImageView categoryPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.subCategoryName);
            categoryPic = itemView.findViewById(R.id.subCategoryPic);
        }
    }
}
