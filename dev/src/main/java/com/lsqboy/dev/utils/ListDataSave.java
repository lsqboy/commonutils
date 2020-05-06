package com.lsqboy.dev.utils;

import android.content.Context;


import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListDataSave {

    /**
     * 保存List
     * <p>
     * Constant.KEY_BUILD_TYPE_MODELS 预约量房类型
     *
     * @param datalist
     */
    public static <T> void setDataList(Context context, List<T> datalist, String key) {
        if (null == datalist || datalist.size() <= 0)
            return;
        clearList(context, key);

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        SharedPreferencesUtil.setValue(context, key, strJson);
    }

    /**
     * 清除List
     */
    public static void clearList(Context context, String key) {
        SharedPreferencesUtil.setValue(context, key, "");
    }

    /**
     * 获取List
     *
     * @return
     */
    public static <T> List<T> getDataList(Context context, String key, final Type type) {
        List<T> datalist = new ArrayList<T>();
        String strJson = SharedPreferencesUtil.getValue(context, key);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, type);
        return datalist;

    }
}