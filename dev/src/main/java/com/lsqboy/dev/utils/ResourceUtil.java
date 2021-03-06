package com.lsqboy.dev.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 资源获取类兼容库
 * <p>
 * Created by lsqboy on 16-12-14.
 * Email：g.lsqboy@gmail.com
 * Copyright (c) 2016,lsqboy.com All Rights Reserved.
 */
public class ResourceUtil {
    /**
     * 获取主题中attr color值
     *
     * @param context      used for getting the color.
     * @param attribute    theme attribute.
     * @param defaultColor default to use.
     * @param theme        主题
     * @return color value
     */
    public static int getAttributeColor(Context context, int attribute, int defaultColor, @Nullable Resources.Theme theme) {
        int themeColor = 0;
        String packageName = context.getPackageName();
        try {
            if (theme == null) {
                Context packageContext = context.createPackageContext(packageName, 0);
                ApplicationInfo applicationInfo =
                        context.getPackageManager().getApplicationInfo(packageName, 0);
                packageContext.setTheme(applicationInfo.theme);
                context.getTheme();
                theme = packageContext.getTheme();
            }
            TypedArray ta = theme.obtainStyledAttributes(new int[]{attribute});
            themeColor = ta.getColor(0, defaultColor);
            ta.recycle();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return themeColor;
    }

    /**
     * Returns a themed color integer associated with a particular resource ID.
     * If the resource holds a complex {@link ColorStateList}, then the default
     * color from the set is returned.
     *
     * @param id    The desired resource identifier, as generated by the aapt
     *              tool. This integer encodes the package, type, and resource
     *              entry. The value 0 is an invalid identifier.
     * @param theme The theme used to style the color attributes, may be
     *              {@code null}.
     * @return A single color value in the form 0xAARRGGBB.
     * @throws Resources.NotFoundException Throws NotFoundException if the given ID does
     *                                     not exist.
     */
    @ColorInt
    @SuppressWarnings({"deprecation"})
    public static int getColor(@NonNull final Context context, @ColorRes int id, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return context.getResources().getColor(id);
        } else {
            return context.getResources().getColor(id, theme);
        }
    }

    @ColorInt
    public static int getColor(@NonNull final Context context, @ColorRes int id) throws Resources.NotFoundException {
        return getColor(context, id, null);
    }

    /**
     * 获取特定主题的指定attribute的dip尺寸<p>
     *
     * @param context  Context
     * @param attrId   attribute ID
     * @param defValue 默认值
     * @param theme    主题，为空即为当前主题
     * @return dip尺寸
     */
    public static float getAttributeDimension(final Context context, @AttrRes int attrId, float defValue, @Nullable Resources.Theme theme) {
        if (theme == null) {
            theme = context.getTheme();
        }
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
        float dimension = ta.getDimension(0, defValue);
        ta.recycle();
        return dimension;
    }

    /**
     * 获取特定主题的指定attribute的像素尺寸<p>
     * 该返回像素值最小不可能为0
     *
     * @param context  Context
     * @param attrId   属性ID
     * @param defValue 默认值
     * @param theme    主题，为空即为当前主题
     * @return pixel 尺寸
     */
    public static int getAttributeDimensionPixelSize(final Context context, @AttrRes int attrId, int defValue, @Nullable Resources.Theme theme) {
        if (theme == null) {
            theme = context.getTheme();
        }
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
        int dimension = ta.getDimensionPixelSize(0, defValue);
        ta.recycle();
        return dimension;
    }

    /**
     * 获取特定主题的指定attribute的像素尺寸<p>
     *
     * @param context  Context
     * @param attrId   属性ID
     * @param defValue 默认值
     * @param theme    主题，为空即为当前主题
     * @return pixel尺寸
     */
    public static int getAttributeDimensionPixelOffset(final Context context, @AttrRes int attrId, int defValue, @Nullable Resources.Theme theme) {
        if (theme == null) {
            theme = context.getTheme();
        }
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
        int dimension = ta.getDimensionPixelOffset(0, defValue);
        ta.recycle();
        return dimension;
    }

    /**
     * 获取主题中attr color值
     * <p><b color="red">需要注意Context的 ActivityContext和ApplicationContext的区别</b></p>
     *
     * @param context      used for getting the color.
     * @param attribute    theme attribute.
     * @param defaultColor default to use.
     * @return color value
     */
    public static int getAttributeColor(Context context, @AttrRes int attribute, int defaultColor) {
        int themeColor;
        Theme theme = context.getTheme();
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attribute});
        themeColor = ta.getColor(0, defaultColor);
        ta.recycle();
        return themeColor;
    }

    /**
     * 兼容获取Drawable
     * <p/>
     * <p>在sdk小于{@link Build.VERSION_CODES#LOLLIPOP}时获取始终当前主题的drawable
     *
     * @param resources Resources
     * @param id        Drawable Res Id
     * @param theme     可以为{@link null},为空为当前主题
     * @return Drawable
     */
    @SuppressWarnings("deprecation")
    @Nullable
    public static Drawable getDrawable(@NonNull final Resources resources, @DrawableRes int id, @Nullable Resources.Theme theme) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = resources.getDrawable(id, theme);
        } else {
            drawable = resources.getDrawable(id);
        }
        return drawable;
    }

    /**
     * Returns a themed color state list associated with a particular resource
     * ID. The resource may contain either a single raw color value or a
     * complex {@link ColorStateList} holding multiple possible colors.
     *
     * @param id    The desired resource identifier of a {@link ColorStateList},
     *              as generated by the aapt tool. This integer encodes the
     *              package, type, and resource entry. The value 0 is an invalid
     *              identifier.
     * @param theme The theme used to style the color attributes, may be
     *              {@code null}.
     * @return A themed ColorStateList object containing either a single solid
     * color or multiple colors that can be selected based on a state.
     * @throws Resources.NotFoundException Throws NotFoundException if the given ID does
     *                                     not exist.
     */
    @SuppressWarnings("deprecation")
    @Nullable
    public ColorStateList getColorStateList(@NonNull Context context, @ColorRes int id, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (theme == null) {
                return context.getResources().getColorStateList(id);
            } else {
                return theme.getResources().getColorStateList(id);
            }
        } else {
            return context.getResources().getColorStateList(id, theme);
        }
    }
}
