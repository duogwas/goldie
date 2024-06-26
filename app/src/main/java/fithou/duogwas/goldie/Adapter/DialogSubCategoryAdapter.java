package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.Activity.FullProductActivity;
import fithou.duogwas.goldie.Entity.Category;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;

public class DialogSubCategoryAdapter extends RecyclerView.Adapter<DialogSubCategoryAdapter.ViewHolder> {
    List<Category> categories;
    Context context;
    List<Long> id = new ArrayList<Long>();

    public DialogSubCategoryAdapter(List<Category> categories, Context context, List<Long> id) {
        this.categories = categories;
        this.context = context;
        this.id=id;
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
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_category_dialog, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category subCategories = categories.get(position);
        if (subCategories == null) {
            return;
        }

        holder.cbCategory.setText(subCategories.getName());

        holder.cbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long idCategory = subCategories.getId();
                if (holder.cbCategory.isChecked()) {
                    id.add(idCategory);
                    Toast.makeText(context,"id add: " + id, Toast.LENGTH_SHORT).show();
                } else {
                    id.remove(idCategory);
                    Toast.makeText(context,"id rm: " + id, Toast.LENGTH_SHORT).show();
                }
                if (onCbCategoryListener != null) {
                    onCbCategoryListener.onCheckBoxClick(id);
                    Toast.makeText(context,"id: " + id, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context,"id null ", Toast.LENGTH_SHORT).show();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCategory = itemView.findViewById(R.id.cbCategory);
        }
    }
}
