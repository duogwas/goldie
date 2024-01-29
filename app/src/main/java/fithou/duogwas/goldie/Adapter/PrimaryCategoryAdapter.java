package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fithou.duogwas.goldie.Model.Category;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import vn.thanguit.toastperfect.ToastPerfect;

public class PrimaryCategoryAdapter extends RecyclerView.Adapter<PrimaryCategoryAdapter.ViewHolder> {
    List<CategoryResponse> categories;
    private RecyclerView.Adapter adapterSubCategories;
    Context context;

    public PrimaryCategoryAdapter(List<CategoryResponse> categoryDomains, Context context) {
        this.categories = categoryDomains;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_primary_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryResponse categoryResponse = categories.get(position);
        if (categoryResponse == null) {
            return;
        }

        holder.categoryName.setText(categoryResponse.getName());
        Glide.with(context)
                .load(categoryResponse.getImageBanner())
                .into(holder.categoryPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.rcvSubCategories.getVisibility() == View.VISIBLE) {
                    holder.rcvSubCategories.setVisibility(View.GONE);
                } else {
                    List<Category> categoriesSubList = categoryResponse.getCategories();
                    holder.rcvSubCategories.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    holder.rcvSubCategories.setLayoutManager(linearLayoutManager1);
                    DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                    holder.rcvSubCategories.addItemDecoration(dividerItemDecoration1);
                    adapterSubCategories = new SubCategoryAdapter(categoriesSubList, context);
                    holder.rcvSubCategories.setAdapter(adapterSubCategories);
                    ToastPerfect.makeText(context, ToastPerfect.INFORMATION, "Bạn đã chọn: " + categoryResponse.getName(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;
        RecyclerView rcvSubCategories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            rcvSubCategories = itemView.findViewById(R.id.rcvSubCategories);
        }
    }
}
