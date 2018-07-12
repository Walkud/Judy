package com.walkud.judy.login.mvp.model;

import com.walkud.judy.login.bean.User;

/**
 * 登录业务Model
 * Created by Zhuliya on 2018/7/2
 */
public class LoginModel {

    /**
     * 登录成功
     *
     * @param userName
     * @param passwrod
     * @return
     */
    public User login(String userName, String passwrod) {
        //模拟登录成功
        return new User("Judy", 1);
    }

}
