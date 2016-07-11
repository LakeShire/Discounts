package com.github.lakeshire.discounts.fragment.login;

import com.github.lakeshire.discounts.model.User;

public interface ILoginView {

    String getUserName();

    String getPassword();

    void onLoginSuccess(User user);

    void onLoginFail();
}
