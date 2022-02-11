package com.qingfeng.utils;

import java.util.Random;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2021/12/25
 */
public class SaltUtils {

    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789!~#@$%^&*()".toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(aChar);
        }
        
        return stringBuilder.toString();
    }
}
