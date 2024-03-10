package fithou.duogwas.goldie.Utils;
//
// Created by duogwas on 10/03/2024.
//


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.Entity.ProductCart;

public class CartManager {

    private static final String CART_PREFS = "cart_prefs";
    private static final String KEY_CART = "key_cart";

    public static void saveCart(Context context, List<ProductCart> productCartList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Chuyển danh sách sản phẩm thành chuỗi JSON
        Gson gson = new Gson();
        String json = gson.toJson(productCartList);

        // Lưu chuỗi JSON vào SharedPreferences
        editor.putString(KEY_CART, json);
        editor.apply();
    }

    public static List<ProductCart> getCart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_CART, null);

        // Nếu không có giá trị, trả về một danh sách rỗng
        if (json == null) {
            return new ArrayList<>();
        }

        // Chuyển chuỗi JSON thành danh sách sản phẩm
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductCart>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}

