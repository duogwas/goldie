package fithou.duogwas.goldie.Utils;
//
// Created by duogwas on 29/03/2024.
//


import android.content.Context;
import android.content.SharedPreferences;

public class DeviceTokenManager {
    private static final String DEVICE_TOKEN_PREFS = "device_token_prefs";
    private static final String KEY_DEVICE_TOKEN = "device_token_key";

    public static void saveDeviceToken(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEVICE_TOKEN_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DEVICE_TOKEN, value);
        editor.apply();
    }

    public static String getDeviceToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEVICE_TOKEN_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DEVICE_TOKEN, null);
    }
}
