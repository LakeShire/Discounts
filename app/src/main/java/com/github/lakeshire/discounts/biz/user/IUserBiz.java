package com.github.lakeshire.discounts.biz.user;

/**
 * 用户相关业务接口
 */
public interface IUserBiz {

    void login(String username, String password, OnLoginListener listener);

}
