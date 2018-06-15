package com.walkud.base.res.rx;

import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.walkud.base.res.rx.transformer.NetTransformer;
import com.walkud.base.res.rx.transformer.ProgressTransformer;
import com.walkud.base.res.rx.transformer.SmartRefreshTransformer;

import io.reactivex.ObservableTransformer;

/**
 * 自定义Transformer 统一封装调用，便于以后维护
 * Created by Zhuliya on 2018/6/15
 */
public class TransformerCompat {

    /**
     * 网络请求ObservableTransformer
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyNetSchedulers() {
        return new NetTransformer<>();
    }

    /**
     * 进度事务
     *
     * @param context
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> progressSchedulers(Context context, Object obj) {
        if (obj instanceof SmartRefreshLayout) {
            return new SmartRefreshTransformer<>((SmartRefreshLayout) obj);
        }
        return new ProgressTransformer<>(context);
    }

}
