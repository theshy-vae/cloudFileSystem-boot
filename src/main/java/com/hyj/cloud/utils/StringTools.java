package com.hyj.cloud.utils;


import org.apache.commons.lang3.RandomStringUtils;

public class StringTools {

    /**
     * 生成随机数
     * @param count 位数
     */
    public static String getRandomNumber(Integer count) {
        return RandomStringUtils.random(count, false, true);
    }

    public static String getRandomString(Integer count) {
        return RandomStringUtils.random(count, true, true);
    }



    public static String getFilename(String filename) {
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            return filename;
        }
        return filename.substring(0, index);
    }

    public static String getFileSuffix(String filename) {
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return filename.substring(index);
    }
}
