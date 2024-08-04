package com.may.gateway.constant;

public class WhiteListConstant {
    private static final String[] CONST_ARRAY_2 = new String[]{"/auth/users/login", "/auth/logout"};

    public static String[] getWhiteList(){
        return CONST_ARRAY_2.clone();
    }
}
