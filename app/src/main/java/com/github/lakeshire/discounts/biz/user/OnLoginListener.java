package com.github.lakeshire.discounts.biz.user;

import com.github.lakeshire.discounts.model.User;

public interface OnLoginListener {
    void onLoginSuccess(User user);
    void onLoginFailed();
}
