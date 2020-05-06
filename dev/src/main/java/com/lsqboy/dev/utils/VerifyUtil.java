package com.lsqboy.dev.utils;

import android.text.TextUtils;
import android.util.Patterns;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title: 字符串工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * 包括如下功能：
 * </p>
 * <p>
 * 1. 判断字符串是否为空
 * </p>
 * <p>
 * 2. 判断是否手机号码
 * </p>
 * <p>
 * 5. 判断是否为纯数字
 * </p>
 * <p>
 * 6. HTML 编码
 * </p>
 * <p>
 * 7. String转拼音
 * </p>
 * <p>
 * 8. 判断字符串是否为拼音
 * </p>
 * <p>
 * 9. 判断字符串是否包含中文
 * </p>
 * <p>
 * 10. 判断是否是邮箱
 * </p>
 * <p>
 * 11. 判断是否是身份证
 * </p>
 * <p>
 * Created by lsqboy on 16-12-14.
 * Email：g.lsqboy@gmail.com
 * Copyright (c) 2016,lsqboy.com All Rights Reserved.
 */
public class VerifyUtil {
    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean validate(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static boolean validateEnglish(String englishStr) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(englishStr);
        return matcher.matches();

    }

    /**
     * 校验身份证ID
     *
     * @param id 身份证号
     * @return 身份证是否校验正确
     */
    public static boolean verifyID(String id) {
        Pattern pattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }

    /**
     * 判断是否是中文
     */
    public static boolean verifyCNStr(String zhongwen) {
        Pattern abs = Pattern.compile("(\\d+)\u4e2a\u6587\u4ef6");

        Matcher matcher = abs.matcher(zhongwen);
        return matcher.matches();
    }



    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }
    // 判断一个字符串是否是中文
    public static boolean isChinese(String str) {
        if (TextUtils.isEmpty(str)) return false;
        for (char c : str.toCharArray()) {
            if (!isChinese(c)) return false;// 有一个中文字符就返回
        }
        return true;
    }

    /**
     * 是否是手机号码
     *
     * @param phone 手机号码
     * @return true 是手机号码 或者 电话号码
     */
    public static boolean isPhoneNumber(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.contains(" ")) {
            phone = phone.replace(" ", "");
        }
        Pattern pattern = Pattern.compile("(^(13\\d|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[^346,\\D]\\d{7})$");
        Matcher matcher = pattern.matcher(phone);
        boolean matches = matcher.matches();
        return matches;
//        if (!matches) {
//            return isPhone(phone);
//        } else {
//            return true;
//        }
    }

    /**
     * 手机号验证
     *
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @return 验证通过返回true
     */
    public static boolean isPhone(final String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }


    /**
     * 是否是验证码
     *
     * @param authCode 验证码
     * @return
     */
    public static boolean isAuthCode(String authCode,int authCodeLenght) {
        if (!TextUtils.isEmpty(authCode) && authCode.length() == authCodeLenght) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验Email
     *
     * @param email email
     * @return
     */
    public static boolean isEmail(String email) {
        Matcher m = Patterns.EMAIL_ADDRESS.matcher(email);
        return m.matches();
    }

    /**
     * 金额格式化 499999999999.94 - > 499,999,999,999.94
     *
     * @param s   金额
     * @param len 小数位数
     * @return 格式后的金额
     */
    public static String formatMoney(String s, int len) {

        if (s == null || s.length() < 1) {
            return "";
        }

        NumberFormat formater = null;
        double num = Double.parseDouble(s);

        if (num < 1)
            return String.format("%.2f", num);

        if (len == 0) {
            formater = new DecimalFormat("###,###");
        } else {

            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");
            for (int i = 0; i < len; i++) {
                buff.append("0");
            }

            formater = new DecimalFormat(buff.toString());
        }

        return formater.format(num);

    }

    public static String join(Collection<String> s, String delimiter) {
        if (s.size() == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (String str : s) {
            sb.append(str).append(delimiter);
        }
        if (sb.length() > 0)
            sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * 由全角转半角
     *
     * @param s
     * @return
     */
    @Deprecated
    public static String toSBC(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127 && c[i] > 32)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节
     *
     * @param input
     * @return
     */
    @Deprecated
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    /**
     * 去除字符串中的所有空格
     *
     * @param str
     * @return
     */
    public static String removeBlank(String str) {
        str = deleteBlock(str, "");
        return str;
    }

    public static String removeBlank(String str, String replace) {
        str = deleteBlock(str, replace);
        return str;
    }

    private static String deleteBlock(String str, String replace) {
        StringBuffer sb = new StringBuffer();
        if (!TextUtils.isEmpty(str)) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char s = str.charAt(i);
                if (0 == s || 32 == s) {
                    if (!TextUtils.isEmpty(replace)) sb.append(replace);
                    continue;
                } else {
                    sb.append(s);
                }
            }
            str = sb.toString();
        }
        return str;
    }
}
