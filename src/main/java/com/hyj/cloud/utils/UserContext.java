package com.hyj.cloud.utils;

public class UserContext {
    private static final ThreadLocal<String> TL = new ThreadLocal<>();

    /**
     * 保存用户信息
     * @param userId 用户id
     */
    public static void setUser(String userId){
        TL.set(userId);
    }

    /**
     * 获取用户
     * @return 用户id
     */
    public static String getUser(){
        return TL.get();
    }

    /**
     * 移除用户信息
     */
    public static void removeUser(){
        TL.remove();
    }
}