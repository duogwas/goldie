package fithou.duogwas.goldie.Adapter;
//
// Created by duogwas on 24/03/2024.
//


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fithou.duogwas.goldie.Fragment.CancelledOrderFragment;
import fithou.duogwas.goldie.Fragment.CompletedOrderFragment;
import fithou.duogwas.goldie.Fragment.ToPayOrderFragment;
import fithou.duogwas.goldie.Fragment.ToReceiveOrderFragment;
import fithou.duogwas.goldie.Fragment.ToShipOrderFragment;

public class OrderPagerAdapter extends FragmentStateAdapter {

    public OrderPagerAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ToPayOrderFragment();
            case 1:
                return new ToShipOrderFragment();
            case 2:
                return new ToReceiveOrderFragment();
            case 4:
                return new CancelledOrderFragment();
            case 3:
            default:
                return new CompletedOrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
