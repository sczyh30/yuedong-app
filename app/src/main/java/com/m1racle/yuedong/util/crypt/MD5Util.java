package com.m1racle.yuedong.util.crypt;

/**
 * Yuedong App Common Library
 * MD5 crypt class
 * @author sczyh30
 */

import com.m1racle.yuedong.util.StringUtil;

import java.security.MessageDigest;

public class MD5Util {
    public static String MD5(String s) {
        if(s != null) {
            try {
                byte[] btInput = s.getBytes();
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                mdInst.update(btInput); // 使用指定的字节更新摘要
                byte[] md = mdInst.digest(); // 获得密文

                return StringUtil.bytes2hex(md);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            System.out.println("#E2:Null String at MD5Util.MD5");
            return "";
        }
    }
}
