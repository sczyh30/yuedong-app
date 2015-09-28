package com.m1racle.yuedong.util.crypt;

import com.m1racle.yuedong.util.StringUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Yuedong App Common Library
 * SHA crypt class
 * @author sczyh30
 * @since 0.0.28
 */
public class SHAUtil {
    public static String SHA1(String decript) {
        if(decript != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                digest.update(decript.getBytes());
                byte messageDigest[] = digest.digest();

                return StringUtil.bytes2hex(messageDigest);
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            System.out.println("#E2:Null String at SHA1Util.SHA1");
            return "";
        }
    }
}
