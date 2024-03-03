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

    public PrimaryCategoryAdapter(List<CategoryResponse> categoryResponses, Context context) {
        this.categories = categoryResponses;
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
        CategoryResponse primaryCategories = categories.get(position);
        if (primaryCategories == null) {
            return;
        }

        holder.categoryName.setText(primaryCategories.getName());

        List<Category> categoriesSubList = primaryCategories.getCategories();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rcvSubCategories.setLayoutManager(linearLayoutManager);
        adapterSubCategories = new SubCategoryAdapter(categoriesSubList, context);
        holder.rcvSubCategories.setAdapter(adapterSubCategories);
//        ToastPerfect.makeText(context, ToastPerfect.INFORMATION, "Bạn đã chọn: " + primaryCategories.getName(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
    }

@Override
public int getItemCount(){
        return categories.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView categoryName;
    RecyclerView rcvSubCategories;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.tvPrimaryCategoryName);
        rcvSubCategories = itemView.findViewById(R.id.rcvSubCategories);
    }
}
}
