package com.m1racle.yuedong.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Yuedong App Common Library
 * String Util Class
 * 字符串操作基本扩展功能
 *
 * @author sczyh30
 */
public class StringUtil {

    /**
     * 判断是否为空字符
     */
    public static boolean isEmpty(String s) {
        return s.equals("");
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    @Deprecated
    public static String erase(String str, int from, int to) {
        return str;
    }

    public static String bytes2hex(byte[] bytes) {
        if(!isNull(bytes)) {
            StringBuilder hexString = new StringBuilder();
            //字节数组转换为十六进制数
            for (byte b : bytes) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();
        }
        else {
            System.out.println("#E2:Null Object when converting at StringUtil.bytes2hex");
            return "";
        }
    }

    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line).append("<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isr.close();
                read.close();
                read = null;
                is.close();
                is = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res.toString();
    }

}
