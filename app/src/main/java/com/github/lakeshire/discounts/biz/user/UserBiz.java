package com.github.lakeshire.discounts.biz.user;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.model.User;
import com.github.lakeshire.discounts.util.HttpUtil;

import java.io.IOException;

public class UserBiz implements IUserBiz {

    @Override
    public void login(final String username, final String password, final OnLoginListener listener) {
        final User user = new User(username, password);
        try {
            HttpUtil.getInstance().post("http://lakeshire.top/user/logon", JSON.toJSONString(user), new HttpUtil.Callback() {
                @Override
                public void onFail(String error) {
                    listener.onLoginFailed();
                }

                @Override
                public void onSuccess(String response) {
                    if (response.equals("User not exist")) {
                        listener.onLoginFailed();
                    } else if (response.equals("Password Error")) {
                        listener.onLoginFailed();
                    } else {
                        User user = JSON.parseObject(response, User.class);
                        listener.onLoginSuccess(user);
                    }
                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}