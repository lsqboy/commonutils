package com.lsqboy.dev.utils;

import android.view.Gravity;

import androidx.annotation.StringRes;

/**
 * ToastUtils
 * <p>
 * Created by lsqboy on 2020/5/6.
 * Email：g.lsqboy@gmail.com
 */
public class ToastUtils {
    public static void showShort(@StringRes final int resId){
        com.blankj.utilcode.util.ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        com.blankj.utilcode.util.ToastUtils.showShort(resId);
    }


    public static void showShort(final CharSequence text){
        com.blankj.utilcode.util.ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        com.blankj.utilcode.util.ToastUtils.showShort(text);
    }


    public static void showLong(final CharSequence text){
        com.blankj.utilcode.util.ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        com.blankj.utilcode.util.ToastUtils.showLong(text);
    }

    public static void showLong(@StringRes final int resId){
        com.blankj.utilcode.util.ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        com.blankj.utilcode.util.ToastUtils.showLong(resId);
    }

}
