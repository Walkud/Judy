package com.walkud.base.res.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.walkud.base.lib.base.MvcFragment;
import com.walkud.base.lib.utils.ReflectionUtils;
import com.walkud.base.res.mvp.base.presenter.BasePresenter;

/**
 * Created by Walkud on 17/2/28.
 */

public abstract class MvpFragment<P extends BasePresenter> extends MvcFragment {
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = ReflectionUtils.getSuperClassGenricType(this, 0);
        if (mPresenter != null) {
            mPresenter.setLifecycleProvider(this);
            mPresenter.setView(this);
            mPresenter.setModel(ReflectionUtils.getSuperClassGenricType(mPresenter, 0));
            mPresenter.setContext(mActivity);
        }
    }
}
