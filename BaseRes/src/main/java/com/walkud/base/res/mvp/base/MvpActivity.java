package com.walkud.base.res.mvp.base;

import android.os.Bundle;

import com.walkud.base.lib.base.MvcActivity;
import com.walkud.base.lib.utils.ReflectionUtils;
import com.walkud.base.res.mvp.base.presenter.BasePresenter;


/**
 * Created by Walkud on 17/2/28.
 */

public class MvpActivity<P extends BasePresenter> extends MvcActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ReflectionUtils.getSuperClassGenricType(this, 0);
        if (mPresenter != null) {
            mPresenter.setLifecycleProvider(this);
            mPresenter.setView(this);
            mPresenter.setModel(ReflectionUtils.getSuperClassGenricType(mPresenter, 0));
            mPresenter.setContext(this);
        }

    }
}
