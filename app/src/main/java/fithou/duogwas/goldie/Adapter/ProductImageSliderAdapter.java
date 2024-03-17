package fithou.duogwas.goldie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import fithou.duogwas.goldie.Entity.ProductImage;
import fithou.duogwas.goldie.R;

public class ProductImageSliderAdapter extends SliderViewAdapter<ProductImageSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<ProductImage> productImages;

    public ProductImageSliderAdapter(List<ProductImage> productImages, Context context) {
        this.context = context;
        this.productImages = productImages;
    }

    public void renewItems(List<ProductImage> sliderItems) {
        this.productImages = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.productImages.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(ProductImage productImage) {
        this.productImages.add(productImage);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_image_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        ProductImage productImage = productImages.get(position);

//        viewHolder.textViewDescription.setText(sliderItem.getId());
//        viewHolder.textViewDescription.setTextSize(16);
//        viewHolder.textViewDescription.setTextColor(Color.WHITE);

        Glide.with(viewHolder.itemView)
                .load(productImage.getLinkImage())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return productImages.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}