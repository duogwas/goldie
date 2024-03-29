package fithou.duogwas.goldie.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.Fragment.CartFragment;
import fithou.duogwas.goldie.Fragment.CategoryFragment;
import fithou.duogwas.goldie.Fragment.HomeFragment;
import fithou.duogwas.goldie.Fragment.ProfileFragment;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Utils.CartManager;
import fithou.duogwas.goldie.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    int countItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        replaceFragment(new HomeFragment());
        countItemInCart();

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.menu_category:
                    replaceFragment(new CategoryFragment());
                    break;
                case R.id.menu_cart:
                    replaceFragment(new CartFragment());
                    break;
                case R.id.menu_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout, fragment);
        fragmentTransaction.commit();
    }

    public static int dpToPx(Context context, int dp) {
        Resources resources = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    public void countItemInCart() {
        List<ProductCart> productCartList = CartManager.getCart(MainActivity.this);
        countItem = productCartList.size();
        if (countItem > 0) {
            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_cart);
            badgeDrawable.setVisible(true);
            badgeDrawable.setVerticalOffset(dpToPx(MainActivity.this, 3));
            badgeDrawable.setNumber(countItem);
            badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
            badgeDrawable.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_cart);
            badgeDrawable.setVisible(true);
            badgeDrawable.setVerticalOffset(dpToPx(MainActivity.this, 3));
            badgeDrawable.setNumber(0);
            badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
            badgeDrawable.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}