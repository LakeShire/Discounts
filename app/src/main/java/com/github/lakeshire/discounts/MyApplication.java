package com.github.lakeshire.discounts;

import android.app.Application;
import android.util.Log;

import com.github.lakeshire.discounts.manager.UserManager;
import com.github.lakeshire.lemon.CrashHandler;
import com.github.lakeshire.lemon.util.SharedPreferencesUtil;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        Logger.initialize(Settings.getInstance().isShowMethodLink(true).isShowThreadInfo(false).setMethodOffset(0).setLogPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT));

        String user = SharedPreferencesUtil.getInstance(this).getString("user");
        if (user != null && !user.isEmpty()) {
            UserManager.setUser(user);
        }
    }
}
