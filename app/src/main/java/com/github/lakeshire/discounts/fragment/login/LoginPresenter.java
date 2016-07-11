package com.github.lakeshire.discounts.fragment.login;

import com.github.lakeshire.discounts.biz.user.IUserBiz;
import com.github.lakeshire.discounts.biz.user.OnLoginListener;
import com.github.lakeshire.discounts.biz.user.UserBiz;
import com.github.lakeshire.discounts.model.User;

public class LoginPresenter {

    private ILoginView mLoginView;
    private IUserBiz mUserBiz;

    public LoginPresenter(ILoginView userLoginView) {
        this.mLoginView = userLoginView;
        this.mUserBiz = new UserBiz();
    }

    public void login() {
        mUserBiz.login(mLoginView.getUserName(), mLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void onLoginSuccess(User user) {
                mLoginView.onLoginSuccess(user);
            }

            @Override
            public void onLoginFailed() {
                mLoginView.onLoginFail();
            }
        });
    }
}
