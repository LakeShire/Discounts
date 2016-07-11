package com.github.lakeshire.discounts.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.fragment.base.DBaseFragment;
import com.github.lakeshire.discounts.manager.UserManager;
import com.github.lakeshire.discounts.model.User;
import com.github.lakeshire.discounts.util.HttpUtil;

import java.io.IOException;

public class RegisterFragment extends DBaseFragment {

    private EditText mEtName;
    private EditText mEtPassword;
    private Button mBtnRegister;
    private EditText mEtEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initUI() {
        setTitle("注册");

        mEtName = (EditText) find(R.id.et_name);
        mEtPassword = (EditText) find(R.id.et_password);
        mEtEmail = (EditText) find(R.id.et_email);
        mBtnRegister = (Button) find(R.id.btn_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                String password = mEtPassword.getText().toString();
                final User user = new User(name, password);
                try {
                    HttpUtil.getInstance().post("http://lakeshire.top/users/add", JSON.toJSONString(user), new HttpUtil.Callback() {
                        @Override
                        public void onFail(String error) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onSuccess(final String response) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (response.equals("Error")) {
                                            Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                                            UserManager.setUser(user);
                                            endFragment();
                                        }
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
