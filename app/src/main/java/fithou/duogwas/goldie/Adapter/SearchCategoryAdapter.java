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

import de.hdodenhof.circleimageview.CircleImageView;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import vn.thanguit.toastperfect.ToastPerfect;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder> {
    List<CategoryResponse> categories;
    Context context;

    public SearchCategoryAdapter(List<CategoryResponse> categoryResponses, Context context) {
        this.categories = categoryResponses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_category, parent, false);
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
                ToastPerfect.makeText(context, ToastPerfect.INFORMATION, "Bạn đã chọn: " + categoryResponse.getName(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
        }
    }
}
