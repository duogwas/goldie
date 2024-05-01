package fithou.duogwas.goldie.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import fithou.duogwas.goldie.Adapter.OrderPagerAdapter;
import fithou.duogwas.goldie.R;

public class MyOrderActivity extends AppCompatActivity {
    ImageView ivBack;
    TabLayout tabOrder;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_order);
        initView();
        setUpTabOrder();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyOrderActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        tabOrder = findViewById(R.id.tabOrder);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new OrderPagerAdapter(this));
    }

    private void setUpTabOrder() {
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabOrder, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Chờ xác nhận");
                        break;
                    case 1:
                        tab.setText("Chờ lấy hàng");
                        break;
                    case 2:
                        tab.setText("Chờ giao hàng");
                        break;
                    case 3:
                        tab.setText("Đã giao");
                        break;
                    case 4:
                        tab.setText("Đã hủy");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }
}