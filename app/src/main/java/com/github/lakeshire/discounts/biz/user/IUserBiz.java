package com.github.lakeshire.discounts.biz.user;

public interface IUserBiz {
    void login(String username, String password, OnLoginListener listener);
}
