package com.lsqboy.dev.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {

    public static String positionToTip(int positionInt) {
        int position = positionInt + 1;
        String temp = "";
        if (position < 10) {
            temp = "0" + position + "/";
        } else {
            temp = String.valueOf(position) + "/";
        }
        return temp;
    }

    public static String positionToTipDot(int positionInt) {
        int position = positionInt + 1;
        String temp = "";
        temp = String.valueOf(position) + ".";
        return temp;
    }

    public static String handle344ToPhone(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.contains(" ")) {
            phone = phone.replace(" ", "");
        }
        return phone;
    }

    public static String handlePhoneTo344(String text) {
        if (!TextUtils.isEmpty(text)) {
            StringBuilder sb = new StringBuilder();
            char space = ' ';
            int indexSpace1 = 3;
            int indexSpace2 = 8;
            //1.取出所有字符，去掉' '和非法字符
            for (int i = 0; i < text.length(); i++) {
                //如果数字数大于11位，去掉后面的数字
                if (sb.length() >= 11) {
                    break;
                }

                //是否合法字符(0~9) （正则表达式）
                Pattern pattern = Pattern.compile("^[0-9]*$");
                Matcher matcher = pattern.matcher(String.valueOf(text.charAt(i)));
                if (text.charAt(i) != space && matcher.matches()) {
                    sb.append(text.charAt(i));
                }
            }

            //2.根据长度追加' '
            if (sb.length() > indexSpace1) {
                sb.insert(indexSpace1, space);
            }
            if (sb.length() > indexSpace2) {
                sb.insert(indexSpace2, space);
            }
            return sb.toString();
        } else {
            return text;
        }
    }

    /**
     * 电话3 4 4格式(即：xxx xxxx xxxx)
     * 电话长度11位数字
     *
     * @param view 输入框
     * @param text 文本
     */
    public static void onTextChanged344(EditText view, String text) {
        if (view == null || text == null || text.length() == 0) return;
        char space = ' ';
        int indexSpace1 = 3;
        int indexSpace2 = 8;
        StringBuilder sb = new StringBuilder();
        //1.取出所有字符，去掉' '和非法字符
        for (int i = 0; i < text.length(); i++) {
            //如果数字数大于11位，去掉后面的数字
            if (sb.length() >= 11) {
                break;
            }

            //是否合法字符(0~9) （正则表达式）
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher matcher = pattern.matcher(String.valueOf(text.charAt(i)));
            if (text.charAt(i) != space && matcher.matches()) {
                sb.append(text.charAt(i));
            }
        }

        //2.根据长度追加' '
        if (sb.length() > indexSpace1) {
            sb.insert(indexSpace1, space);
        }
        if (sb.length() > indexSpace2) {
            sb.insert(indexSpace2, space);
        }
        //3.设置文本和光标位置
        if (!sb.toString().equals(text)) {
            view.setText(sb.toString());
            view.setSelection(sb.length());
        }
    }

    /**
     * 深拷贝
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    /*
     * 隐藏软键盘
     */
    public static void hideSoftwareKeyBoard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0); // 强制隐藏键盘
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态==="
                            + networkInfo[i].getState());
                    System.out.println(i + "===类型==="
                            + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * 获取当前手机语言
     */
    public static String getCurrentPhoneLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    /*
     * 启动第三方程序
     */
    public static boolean startActivitySafe(Context context, String pkg, Intent intent) {
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(pkg);
        List<ResolveInfo> ris = context.getPackageManager().queryIntentActivities(
                intent, 0);
        if (ris.size() > 0) {
            intent.setComponent(new ComponentName(pkg,
                    ris.get(0).activityInfo.name));
            context.startActivity(intent);
            return true;
        }
        return false;
    }

}
