package fithou.duogwas.goldie.Fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fithou.duogwas.goldie.Activity.CheckOutActivity;
import fithou.duogwas.goldie.Activity.MainActivity;
import fithou.duogwas.goldie.Adapter.CartAdapter;
import fithou.duogwas.goldie.Entity.ProductCart;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Utils.CartManager;


public class CartFragment extends Fragment {
    ConstraintLayout clCartIsEmpty, clBtnCart, clCart;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    TextView tvTotalPrice;
    AppCompatButton btnCheckout;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        loadAllCart();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CheckOutActivity.class));
            }
        });
    }

    private void initView() {
        clCartIsEmpty = getView().findViewById(R.id.clCartIsEmpty);
        clBtnCart = getView().findViewById(R.id.clBtnCart);
        tvTotalPrice = getView().findViewById(R.id.tvTotalPrice);
        btnCheckout = getView().findViewById(R.id.btnCheckout);
        clCart = getView().findViewById(R.id.clCart);
    }

    private void loadAllCart() {
        recyclerView = getView().findViewById(R.id.rcvCart);
        List<ProductCart> productCartList = CartManager.getCart(getContext());
        if (productCartList.size() == 0) {
            clCartIsEmpty.setVisibility(View.VISIBLE);
            clBtnCart.setVisibility(View.GONE);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            cartAdapter = new CartAdapter(productCartList, getContext());
            recyclerView.setAdapter(cartAdapter);
            updateTotalPrice();

            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    productCartList.remove(position);
                    CartManager.saveCart(getContext(), productCartList);
                    cartAdapter.notifyDataSetChanged();
                    if (productCartList.size() == 0) {
                        clCartIsEmpty.setVisibility(View.VISIBLE);
                        clBtnCart.setVisibility(View.GONE);
                    }
                    updateTotalPrice();
                    refreshCountItemCart();
                }

                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                    Drawable deleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_delete);
                    ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.redDelete));

                    View itemView = viewHolder.itemView;
                    int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                    int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                    int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();

                    if (dX < 0) { // Swiping to the left
                        int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
                        int iconRight = itemView.getRight() - iconMargin;
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                        background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        background.draw(c);
                        if (-dX > itemView.getWidth() / 7) {
                            // Vẽ biểu tượng xóa và nền
                            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                            background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                            background.draw(c);
                            deleteIcon.draw(c);
                        }
                    } else { // No swipe
                        background.setBounds(0, 0, 0, 0);
                    }
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);

            cartAdapter.setQuantityChangeListener(new CartAdapter.OnQuantityChangeListener() {
                @Override
                public void onQuantityChange(int position, int quantity) {
                    updateTotalPrice();
                }
            });
        }
    }

    private void refreshCountItemCart() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.countItemInCart();
        }
    }

    private String formatPrice(Double totalPrice) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(totalPrice);
        return formattedPrice;
    }

    private void updateTotalPrice() {
        List<ProductCart> productCartList = CartManager.getCart(getContext());
        Double total = 0.0;
        for (ProductCart cartProduct : productCartList) {
            total += cartProduct.getQuantity() * cartProduct.getProduct().getPrice();
        }
        tvTotalPrice.setText(formatPrice(total));
    }

}