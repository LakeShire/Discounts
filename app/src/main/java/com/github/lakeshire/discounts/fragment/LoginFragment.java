package com.github.lakeshire.discounts.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.manager.UserManager;
import com.github.lakeshire.discounts.model.User;
import com.github.lakeshire.discounts.util.HttpUtil;

import java.io.IOException;

/**
 * Created by nali on 2016/6/2.
 */
public class LoginFragment extends DBaseFragment {

    private EditText mEtName;
    private EditText mEtPassword;
    private Button mBtnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    void initUI() {
        mEtName = (EditText) find(R.id.et_name);
        mEtPassword = (EditText) find(R.id.et_password);
        mBtnLogin = (Button) find(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                String password = mEtPassword.getText().toString();
                final User user = new User(name, password);
                try {
                    HttpUtil.getInstance().post("http://lakeshire.top/users/logon", JSON.toJSONString(user), new HttpUtil.Callback() {
                        @Override
                        public void onFail(String error) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onSuccess(String response) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                                        UserManager.setUser(user);
                                        endFragment();
                                    }
                                });
                            }
                        }
                    }, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
    }
}
