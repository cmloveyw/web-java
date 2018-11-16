package com.heitian.ssm.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @version 1.0
 * @类名: Md5Uils.java
 * @描述: MD5加密
 * @创建人: CM
 * @创建时间: 2018-10-22
 */
public class Md5Uils {
    public static String stringToMd5(String str) {
        MessageDigest messageDigest = null;
        String result;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (messageDigest != null) {
            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuffer = new StringBuilder();
            for (byte by : bytes) {
                //0xff 的功能其实是补全32位int的
                if (Integer.toHexString(0xFF & by).length() == 1) {
                    //System.out.println("0");
                    stringBuffer.append("0").append(Integer.toHexString(0xFF & by));
                } else {
                    stringBuffer.append(Integer.toHexString(0xFF & by));
                }
                //stringBuffer.append(Integer.toHexString(0xFF & by));
            }
            result = stringBuffer.toString();
            return result;
        } else {
            return null;
        }
    }

    /**
     * 测试
     * 时间:   2018-10-22 16:03
     * 作者:   CM
     */
    public static void main(String[] args) {
        String str = "12345678";
        str = stringToMd5(str);
        System.out.println(str);
    }
}
