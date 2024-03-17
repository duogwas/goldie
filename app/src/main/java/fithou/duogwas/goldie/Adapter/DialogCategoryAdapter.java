package fithou.duogwas.goldie.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fithou.duogwas.goldie.Activity.FullProduct;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.CategoryResponse;
import vn.thanguit.toastperfect.ToastPerfect;

public class DialogCategoryAdapter extends RecyclerView.Adapter<DialogCategoryAdapter.ViewHolder> {
    List<CategoryResponse> categories;
    Context context;
    List<Long> id = new ArrayList<Long>();

    public DialogCategoryAdapter(List<CategoryResponse> categoryResponses, Context context) {
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
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_dialog, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryResponse categoryResponse = categories.get(position);
        if (categoryResponse == null) {
            return;
        }

        holder.cbCategory.setText(categoryResponse.getName());
        holder.cbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long idCategory = categoryResponse.getId();
                if (holder.cbCategory.isChecked()) {
                    id.add(idCategory);
//                    Toast.makeText(context, "id: " + id.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    id.remove(idCategory);
//                    Toast.makeText(context, "id: " + id.toString(), Toast.LENGTH_SHORT).show();
                }
                if (onCbCategoryListener != null) {
                    onCbCategoryListener.onCheckBoxClick(id);
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
