package com.walkud.base.res.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 头部处理拦截器
 * Created by Zhuliya on 2018/6/11
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //在此添加默认头部参数
        return chain.proceed(chain.request());
    }
}
