package com.walkud.base.lib.network;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * Retorfit 建造者配置信息
 * Created by Zhuliya on 2018/6/11
 */
public class RetrofitConfig {

    OkHttpClient okHttpClient;
    /**
     * Retrofit基础Url
     */
    String baseUrl;

    /**
     * 序列化转换工厂集合
     */
    final List<Converter.Factory> converterFactories = new ArrayList<>();
    /**
     * 响应适配器工厂集合
     */
    final List<CallAdapter.Factory> adapterFactories = new ArrayList<>();

    public class Builder {

        RetrofitConfig config = new RetrofitConfig();


        public Builder setOkHttpClient(OkHttpClient okHttpClient) {
            config.okHttpClient = okHttpClient;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            config.baseUrl = baseUrl;
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            adapterFactories.add(factory);
            return this;
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            converterFactories.add(factory);
            return this;
        }

        public RetrofitConfig build() {
            return config;
        }
    }

}
