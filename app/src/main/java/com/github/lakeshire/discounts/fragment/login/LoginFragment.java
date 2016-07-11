package com.github.lakeshire.discounts.fragment.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.fragment.base.DBaseFragment;
import com.github.lakeshire.discounts.manager.UserManager;
import com.github.lakeshire.discounts.model.User;

public class LoginFragment extends DBaseFragment implements ILoginView {

    private EditText mEtName;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private LoginPresenter mLoginPresenter = new LoginPresenter(this);

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
        setTitle("登录");
        mEtName = (EditText) find(R.id.et_name);
        mEtPassword = (EditText) find(R.id.et_password);
        mBtnLogin = (Button) find(R.id.btn_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login();
            }
        });
    }

    @Override
    public String getUserName() {
        return mEtName.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public void onLoginSuccess(final User user) {
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

    @Override
    public void onLoginFail() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
