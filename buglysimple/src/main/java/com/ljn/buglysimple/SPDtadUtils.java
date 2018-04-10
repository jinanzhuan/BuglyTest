package com.ljn.buglysimple;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2017/11/09
 *     desc   : 这是针对医生端的sp
 *     modify :
 * </pre>
 */

public class SPDtadUtils {
    private static final String DOCTOR_SP_NAME = "doctor";
    private static final String PATIENT_SP_NAME = "patient";
//========================================常规的存储=====================================================

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(DOCTOR_SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(DOCTOR_SP_NAME, MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 用于默认值为true的场景
     * @param context
     * @param key
     * @return
     */
    public static boolean getBooleanDefaultTrue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(DOCTOR_SP_NAME, MODE_PRIVATE);
        return sp.getBoolean(key, true);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(DOCTOR_SP_NAME, MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(DOCTOR_SP_NAME, MODE_PRIVATE);
        return sp.getString(key, "");
    }
//========================================存储user相关====================================================
    /**
     * 用来存储user相关信息(key值已经写死到里面就是user)
     * @param context
     * @param name 用户的huid
     * @param value
     */
    public static void putUserString(Context context, String name, String value) {
        SharedPreferences sp = context.getSharedPreferences(name, MODE_PRIVATE);
        sp.edit().putString("user", value).commit();
    }

    /**
     * 用来取得user的相关信息(key值已经写死到里面就是user)
     * @param context
     * @param name 用户的huid
     * @return
     */
    public static String getUserString(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences(name, MODE_PRIVATE);
        return sp.getString("user", "");
    }

//=====================================为了兼容老版本的存储================================================

    /**
     * 为了兼容老版本，不建议使用
     * @param context
     * @param key
     * @return
     */
    public static boolean getOldVsersionBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("global", MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 为了兼容老版本，不建议使用
     * @param context
     * @param key
     * @return
     */
    public static String getOldVsersionString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("global", MODE_PRIVATE);
        return sp.getString(key, "");
    }

//=====================================针对讯飞存储相关====================================================

    public static void putXFString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PATIENT_SP_NAME, MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getXFString(Context context, String key, String defaults) {
        try {
            SharedPreferences sp = context.getSharedPreferences(PATIENT_SP_NAME, MODE_PRIVATE);
            return sp.getString(key, defaults);
        } catch (Exception e) {
            e.printStackTrace();
            return defaults;
        }
    }
}
