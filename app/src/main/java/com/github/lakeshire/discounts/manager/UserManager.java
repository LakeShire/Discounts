package com.github.lakeshire.discounts.manager;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.model.User;

public class UserManager {

    private static User sUser;

    public static User getUser() {
        return sUser;
    }

    public static void setUser(User user) {
        sUser = user;
    }

    public static void setUser(String string) {
        sUser = JSON.parseObject(string, User.class);
    }
}
