package com.lsqboy.dev.utils;

import android.content.Context;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * DisplayMetricsUtil
 * <p>
 * Created by lsqboy on 16-12-14.
 * Email：g.lsqboy@gmail.com
 * Copyright (c) 2016,lsqboy.com All Rights Reserved.
 */
public class DisplayMetricsUtil {

    /**
     * 转换像素尺寸为dip尺寸
     *
     * @param context Context
     * @param pixel   像素尺寸
     * @return dip尺寸
     */
    public static float pixelToDip(@NonNull final Context context, @IntRange(from = 0) int pixel) {
        float density = context.getResources().getDisplayMetrics().density;
        return pixel / density;
    }

    /**
     * 转换dip尺寸为pixel尺寸
     *
     * @param context Context
     * @param dip     dip尺寸
     * @return pixel尺寸
     */
    public static int dipToPixel(@NonNull final Context context, @FloatRange(from = 0f) float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) ((density * dip) + 0.5f);
    }
}
