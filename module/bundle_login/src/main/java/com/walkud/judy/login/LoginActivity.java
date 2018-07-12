package com.walkud.judy.login;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;

import com.walkud.base.lib.base.MvcActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录UI
 */
public class LoginActivity extends MvcActivity {

    @BindView(R2.id.user_name_et)
    AppCompatEditText userNameEt;
    @BindView(R2.id.password_et)
    AppCompatEditText passwordEt;
    @BindView(R2.id.login_btn)
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bl_login);
        ButterKnife.bind(this);

        userNameEt.setText("username");
        passwordEt.setText("password");

    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick(R2.id.login_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R2.id.login_btn://登录按钮
                showToast(userNameEt.getText().toString() + "," + passwordEt.getText().toString());
                break;
        }
    }
}
