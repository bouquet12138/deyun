package com.example.baselib.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {

    public static String getMD5(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());

            String md5Str = new BigInteger(1, md.digest()).toString(16);
            if (md5Str.length() < 32) {
                md5Str = 0 + md5Str;
            }
            return md5Str;
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }
    }

    public static String encodeData(int year, int month, int day) {


        String[] arr = {"ads", "dg", "dog", "month", "as", "dga", "sga", "sa", "ss"};

        year = year * 3 % 1234;
        month = month + 100;

        return year + arr[year % arr.length] + month +
                arr[month % arr.length] + day + arr[day % arr.length];
    }

}
