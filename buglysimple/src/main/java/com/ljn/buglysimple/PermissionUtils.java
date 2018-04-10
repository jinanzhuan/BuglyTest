package com.ljn.buglysimple;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * @description 1、使用此utils的前提是在build.gradle中添加//权限管理的依赖compile 'com.yanzhenjie:permission:1.0.5'
 *              2、使用hasPermission,FailedForPermissions以及onRequestPermissionsResult中的上下文可以是activity,fragment，需要传入正确的。
 *              尤其是在fragment中一定要使用fragment作为上下文，否则在onRequestPermissionsResult无回调。
 * Created by ljn on 2017/3/29.
 */

public class PermissionUtils {
    /**
     * 判断是否有相关权限，如果没有权限，则请求相关权限
     * @param context 此处为Activity
     * @param requsetCode 请求码
     * @param permissions 权限
     * @return
     */
    public static boolean hasPermission(Activity context, int requsetCode, String... permissions){
        if(!AndPermission.hasPermission(context, permissions)) {
            AndPermission.with(context)
                    .requestCode(requsetCode)
                    .permission(permissions)
                    .send();
            return false;
        }
        return true;
    }

    /**
     * 判断是否有相关权限，如果没有权限，则请求相关权限
     * @param context 此处为Fragment
     * @param requsetCode 请求码
     * @param permissions 权限
     * @return
     */
    public static boolean hasPermission(Fragment context, int requsetCode, String... permissions){
        if(!AndPermission.hasPermission(context.getActivity(), permissions)) {
            AndPermission.with(context)
                    .requestCode(requsetCode)
                    .permission(permissions)
                    .send();
            return false;
        }
        return true;
    }

    /**
     * 判断是否有相关权限，如果没有权限，则请求相关权限
     * @param context 此处为android.support.v4.app.Fragment
     * @param requsetCode 请求码
     * @param permissions 权限
     * @return
     */
    public static boolean hasPermission(android.support.v4.app.Fragment context, int requsetCode, String... permissions){
        if(!AndPermission.hasPermission(context.getActivity(), permissions)) {
            AndPermission.with(context)
                    .requestCode(requsetCode)
                    .permission(permissions)
                    .send();
            return false;
        }
        return true;
    }

    /**
     * Parse the request results.
     * @param activity     {@link Activity}.
     * @param requestCode  request code.
     * @param permissions  all permissions.
     * @param grantResults results.
     */
    public static void onRequestPermissionsResult(@NonNull Activity activity, int requestCode, @NonNull String[]
            permissions, int[] grantResults) {
        AndPermission.onRequestPermissionsResult(activity, requestCode, permissions, grantResults);
    }

    /**
     * Parse the request results.
     * @param fragment     {@link android.support.v4.app.Fragment}.
     * @param requestCode  request code.
     * @param permissions  all permissions.
     * @param grantResults results.
     */
    public static void onRequestPermissionsResult(@NonNull android.support.v4.app.Fragment fragment, int
            requestCode, @NonNull String[] permissions, int[] grantResults) {
        AndPermission.onRequestPermissionsResult(fragment, requestCode, permissions, grantResults);
    }

    /**
     * Parse the request results.
     *
     * @param fragment     {@link Fragment}.
     * @param requestCode  request code.
     * @param permissions  all permissions.
     * @param grantResults results.
     */
    public static void onRequestPermissionsResult(@NonNull Fragment fragment, int requestCode,
                                                  @NonNull String[] permissions, int[] grantResults) {
        AndPermission.onRequestPermissionsResult(fragment, requestCode, permissions, grantResults);
    }

    /**
     * 请求权限失败后，默认开启权限的对话框
     * @param context
     * @param requsetCode
     * @param deniedPermissions
     */
    public static void FailedForPermissions(Activity context, int requsetCode, List<String> deniedPermissions){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context, requsetCode)
                    .setTitle("权限申请失败")
                    .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                    .setPositiveButton("设置")
                    .show();
        }
    }

    /**
     * Parse the request results.
     *
     * @param requestCode  request code.
     * @param permissions  one or more permissions.
     * @param grantResults results.
     * @param listener     {@link PermissionListener}.
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[]
            grantResults, @NonNull PermissionListener listener) {
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    /**
     * 求权限失败后，默认开启权限(可以设置提示信息)对话框
     * @param context 此处为activity
     * @param requsetCode
     * @param deniedPermissions
     * @param message
     */
    public static void FailedForPermissions(Activity context, int requsetCode, List<String> deniedPermissions, String message){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context, requsetCode)
                    .setTitle("权限申请失败")
                    .setMessage(message)
                    .setPositiveButton("设置")
                    .show();
        }
    }

    /**
     * 求权限失败后，默认开启权限(可以设置提示信息)对话框
     * @param context 此处为android.support.v4.app.Fragment
     * @param requsetCode
     * @param deniedPermissions
     * @param message
     */
    public static void FailedForPermissions(android.support.v4.app.Fragment context, int requsetCode, List<String> deniedPermissions, String message){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context.getActivity(), requsetCode)
                    .setTitle("权限申请失败")
                    .setMessage(message)
                    .setPositiveButton("设置")
                    .show();
        }
    }

    /**
     * 求权限失败后，默认开启权限(可以设置提示信息)对话框
     * @param context 此处为android.app.Fragment
     * @param requsetCode
     * @param deniedPermissions
     * @param message
     */
    public static void FailedForPermissions(@NonNull Fragment context, int requsetCode, String message, @NonNull String... deniedPermissions){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context.getActivity(), requsetCode)
                    .setTitle("权限申请失败")
                    .setMessage(message)
                    .setPositiveButton("设置")
                    .show();
        }
    }


    /**
     * 求权限失败后，默认开启权限(可以设置提示信息)对话框
     * @param context 此处为activity
     * @param requsetCode
     * @param deniedPermissions
     * @param message
     */
    public static void FailedForPermissions(Activity context
            , int requsetCode
            , List<String> deniedPermissions
            , String message
            , String negativeButtonText
            , DialogInterface.OnClickListener listener){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context, requsetCode)
                    .setTitle("权限申请失败")
                    .setMessage(message)
                    .setPositiveButton("设置")
                    .setNegativeButton(negativeButtonText, listener)
                    .show();
        }
    }



    /**
     * 请求权限失败后，可以设置标题和内容的对话框
     * @param context
     * @param requsetCode
     * @param deniedPermissions
     * @param title
     * @param message
     */
    public static void FailedForPermissions(Activity context, int requsetCode
            , List<String> deniedPermissions, String title, String message){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context, requsetCode)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("设置")
                    .show();
        }
    }

    /**
     * 请求权限失败后，可以设置标题和内容以及确认内容的对话框
     * @param context
     * @param requsetCode
     * @param deniedPermissions
     * @param title
     * @param message
     * @param positiveButtonText
     */
    public static void FailedForPermissions(Activity context, int requsetCode
            , List<String> deniedPermissions, String title, String message, String positiveButtonText){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context, requsetCode)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonText)
                    .show();
        }
    }

    /**
     * 请求权限失败后，可以设置标题、内容、确认内容、取消内容以及取消监听的对话框
     * @param context 此处为activity
     * @param requsetCode
     * @param deniedPermissions
     * @param title
     * @param message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param listener
     */
    public static void FailedForPermissions(Activity context
            , int requsetCode
            , List<String> deniedPermissions
            , String title
            , String message
            , String positiveButtonText
            , String negativeButtonText
            , DialogInterface.OnClickListener listener){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context, requsetCode)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonText)
                    .setNegativeButton(negativeButtonText, listener)
                    .show();
        }
    }

    /**
     * 请求权限失败后，可以设置标题、内容、确认内容、取消内容以及取消监听的对话框
     * @param context 此处为android.support.v4.app.Fragment
     * @param requsetCode
     * @param deniedPermissions
     * @param title
     * @param message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param listener
     */
    public static void FailedForPermissions(android.support.v4.app.Fragment context
            , int requsetCode
            , List<String> deniedPermissions
            , String title
            , String message
            , String positiveButtonText
            , String negativeButtonText
            , DialogInterface.OnClickListener listener){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context.getActivity(), requsetCode)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonText)
                    .setNegativeButton(negativeButtonText, listener)
                    .show();
        }
    }

    /**
     * 请求权限失败后，可以设置标题、内容、确认内容、取消内容以及取消监听的对话框
     * @param context 此处为android.app.Fragment
     * @param requsetCode
     * @param deniedPermissions
     * @param title
     * @param message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param listener
     */
    public static void FailedForPermissions(@NonNull Fragment context
            , int requsetCode
            , String title
            , String message
            , String positiveButtonText
            , String negativeButtonText
            , DialogInterface.OnClickListener listener
            , String... deniedPermissions){
        if(AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
            AndPermission.defaultSettingDialog(context.getActivity(), requsetCode)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonText)
                    .setNegativeButton(negativeButtonText, listener)
                    .show();
        }
    }
}
