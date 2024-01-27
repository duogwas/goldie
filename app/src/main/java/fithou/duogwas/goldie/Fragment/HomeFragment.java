package fithou.duogwas.goldie.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bdtopcoder.smart_slider.SliderAdapter;
import com.bdtopcoder.smart_slider.SliderItem;

import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.R;
import vn.thanguit.toastperfect.ToastPerfect;

public class HomeFragment extends Fragment {
    ViewPager2 viewPager2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
        Slide();

    }

    private void AnhXa() {
        viewPager2 = getView().findViewById(R.id.smartSlider);
    }
    private void Slide() {
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.banner_1,"image 1"));
        sliderItems.add(new SliderItem(R.drawable.banner_2,"image 2"));

        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2,3500));

        new SliderAdapter((position, title, view1) -> {
            if(title=="image 1"){
                ToastPerfect.makeText(getContext(), ToastPerfect.SUCCESS, "Today is a beautiful day!", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
            }
        });
    }
}