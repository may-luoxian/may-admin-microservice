package com.may.utils.getuserdetails;

import com.may.utils.model.dto.UserDetailsShareDTO;

/**
 * 共享线程，用于用户信息共享
 */
public class UserInfoContext {
    private static ThreadLocal<UserDetailsShareDTO> userInfo = new ThreadLocal<>();

    public static UserDetailsShareDTO getUser() {
        return (UserDetailsShareDTO) userInfo.get();
    }

    public static void setUser(UserDetailsShareDTO user) {
        userInfo.set(user);
    }

    public static void remove(){
        userInfo.remove();
    }
}
