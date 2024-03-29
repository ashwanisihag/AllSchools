package com.track24x7.allSchools.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ashwani Sihag on 26-05-2017.
 */

public class Pref {

    private static final String PrefDB = "fauzi";
    private static final String PerPrefDB = "perfauzi";


    public static final String FCM_REGISTRATION_TOKEN = "fcm_registration_token";


    public static void SetStringPref(Context context, String KEY, String Value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(KEY, Value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GetStringPref(Context context, String KEY, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getString(KEY, defValue);
    }

    public static void SetBooleanPref(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }

    public static void setPermanentBoolean(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PerPrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }

    public static boolean getPermanentBoolean(Context context, String KEY, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(PerPrefDB, MODE_PRIVATE);
        return sp.getBoolean(KEY, defValue);
    }

    public static int GetIntPref(Context context, String KEY, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getInt(KEY, defValue);
    }

    public static void SetIntPref(Context context, String KEY, int Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY, Value);
        editor.commit();
    }

    public static boolean GetBooleanPref(Context context, String KEY, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getBoolean(KEY, defValue);
    }

    public static void clearSharedPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void SaveDeviceToken(Context context, String Value) {
        SharedPreferences sp = context.getSharedPreferences("fauzidevicetoken.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("devicetoken", Value);
        editor.commit();
    }

    public static String GetDeviceToken(Context context, String defValue) {
        SharedPreferences sp = context.getSharedPreferences("fauzidevicetoken.txt", MODE_PRIVATE);
        return sp.getString("devicetoken", defValue);
    }

}
