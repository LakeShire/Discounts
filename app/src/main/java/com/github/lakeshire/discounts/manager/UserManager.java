package com.github.lakeshire.discounts.manager;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.model.User;

/**
 * Created by nali on 2016/6/3.
 */
public class UserManager {

    private static User sUser;

    public static User getUser() {
        return sUser;
    }

    public static void setUser(User user) {
        sUser = user;
    }

    public static void setUser(String string) {
        sUser = (User) JSON.toJSON(string);
    }
}
