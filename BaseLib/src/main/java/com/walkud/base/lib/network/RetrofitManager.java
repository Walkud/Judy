package com.walkud.base.lib.network;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.socks.library.KLog;
import com.walkud.base.lib.BuildConfig;
import com.walkud.base.lib.config.Config;
import com.walkud.base.lib.utils.AppContextUtil;
import com.walkud.base.lib.utils.NetUtil;
import com.walkud.base.lib.utils.gson.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 请求
 * Created by laucherish on 16/3/15.
 */
public class RetrofitManager {

    //超时时间(秒)
    public static final int OUT_TIME_SECONDS = 60;
    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;
    //缓存大小50Mb
    public static final int CACHE_SIZE = 1024 * 1024 * 50;
    //缓存事件控制Header
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "public, only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static RetrofitManager retrofitManager;
    private Retrofit retrofit;

    public void init(RetrofitConfig config) {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager(config);
                }
            }
        }
    }

    /**
     * 创建对应的Api
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T createApi(Class cls) {
        return (T) retrofitManager.retrofit.create(cls);
    }

    private RetrofitManager(RetrofitConfig config) {

        if (config.okHttpClient == null) {
            throw new NullPointerException("RetrofitConfig okHttpClient is null!");
        }

        if (config.adapterFactories.isEmpty()) {
            config.adapterFactories.add(RxJava2CallAdapterFactory.create());
        }

        if (config.converterFactories.isEmpty()) {
            config.converterFactories.add(GsonConverterFactory.create(GsonUtil.buildGson()));
        }


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .client(config.okHttpClient);

        //添加响应适配器
        for (CallAdapter.Factory adapterFactory : config.adapterFactories) {
            builder.addCallAdapterFactory(adapterFactory);
        }

        //添加序列化转换工厂
        for (Converter.Factory converterFactory : config.converterFactories) {
            builder.addConverterFactory(converterFactory);
        }

        retrofit = builder.build();
    }

}
