package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.Entity.Category;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;

public class DialogPrimaryCategoryAdapter extends RecyclerView.Adapter<DialogPrimaryCategoryAdapter.ViewHolder> {
    List<CategoryResponse> categories;
    Context context;
    List<Long> id = new ArrayList<Long>();

    public DialogPrimaryCategoryAdapter(List<CategoryResponse> categoryResponses, Context context) {
        this.categories = categoryResponses;
        this.context = context;
    }

    public interface OnCbCategoryListener {
        void onCheckBoxClick(List<Long> idCategory);
    }

    // Trong adapter của bạn, khởi tạo một instance của interface này
    private OnCbCategoryListener onCbCategoryListener;

    // Phương thức để set listener
    public void setOnCbCategoryListener(OnCbCategoryListener listener) {
        this.onCbCategoryListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_primary_category_dialog, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryResponse categoryResponse = categories.get(position);
        if (categoryResponse == null) {
            return;
        }

        holder.cbCategory.setText(categoryResponse.getName());

//        List<Category> categoriesSubList = categoryResponse.getCategories();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
//        holder.rcvSubCategories.setLayoutManager(gridLayoutManager);
//        DialogSubCategoryAdapter adapterSubCategories = new DialogSubCategoryAdapter(categoriesSubList, context, id);
//        holder.rcvSubCategories.setAdapter(adapterSubCategories);
//        holder.rcvSubCategories.setVisibility(View.GONE);

        holder.cbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(holder.rcvSubCategories.getVisibility()==View.GONE){
//                    holder.rcvSubCategories.setVisibility(View.VISIBLE);
//                }
//                else {
//                    holder.rcvSubCategories.setVisibility(View.GONE);
//                }

                Long idCategory = categoryResponse.getId();
                if (holder.cbCategory.isChecked()) {
                    id.add(idCategory);
                } else {
                    id.remove(idCategory);
                }
                if (onCbCategoryListener != null) {
                    onCbCategoryListener.onCheckBoxClick(id);
                    Toast.makeText(context,"id: " + id, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatCheckBox cbCategory;
//        RecyclerView rcvSubCategories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCategory = itemView.findViewById(R.id.cbCategory);
//            rcvSubCategories = itemView.findViewById(R.id.rcvSubCategories);
        }
    }
}
