package com.walkud.base.res.network.interceptor;

import android.text.TextUtils;

import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Body加密拦截器,用于处理参数加密
 * <p>
 * Created by Zhuliya on 2018/6/11
 */
public class BodyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
//        if (!(request.body() instanceof MultipartBody) && "POST".equals(request.method())) {
//
//                Request.Builder builder = request.newBuilder();
//
//                String body;
//                Headers headers;
//                //判断Body类型，区别解析和加密Body方式
//                Map<String, String> bodyMap = getBody(request);
//                body = getBodyString(bodyMap);
//                AesHelper aesHelper = new AesHelper();
//                headers = aesHelper.buildHeader(bodyMap);//添加头部数据
//
//                //加密Body
//                String encryptBody = aesHelper.encryptStr(body);
//
//                //构建加密后的Request
//                Request newRequest = builder.headers(headers).post(RequestBody.create(null, encryptBody)).build();
//                return handleResponse(chain.proceed(newRequest), body);
//            } else {
//                //文件上传不使用https协议
//                if (request.body() instanceof MultipartBody) {
//                    request = request.newBuilder().url(request.url().newBuilder().scheme("http").build()).build();
//                }
//                return chain.proceed(request);
//            }
    }


    /**
     * 是否输入FromBody
     *
     * @param request
     * @return
     */
    private boolean isFromBody(Request request) {
        RequestBody requestBody = request.body();
        return (requestBody != null) && (requestBody instanceof FormBody);
    }


    /**
     * 获取Body Map
     *
     * @param request
     * @return
     */
    private Map<String, String> getBody(Request request) throws IOException {

        RequestBody requestBody = request.body();
        if (isFromBody(request)) {
            Map<String, String> map = new HashMap<>();
            FormBody formBody = (FormBody) requestBody;
            for (int i = 0; i < formBody.size(); i++) {
                String value = formBody.value(i);
                if (!TextUtils.isEmpty(value)) {
                    map.put(formBody.name(i), value);
                }
            }
            return map;
        }

        return getBodyToMap(request);
    }


    /**
     * 获取Body
     *
     * @param bodyMap
     * @return
     */
    private String getBodyString(Map<String, String> bodyMap) {
        return new JSONObject(bodyMap).toString();
    }

    /**
     * 获取Body String
     *
     * @param request
     * @return
     * @throws IOException
     */
    private Map<String, String> getBodyToMap(Request request) throws IOException {
        String body = "";
        RequestBody requestBody = request.body();

        if (requestBody != null) {
            Buffer sink = new Buffer();
            requestBody.writeTo(sink);
            body = sink.readUtf8();
        }

        return converFormBody(body);
    }

    /**
     * 转换Body参数
     *
     * @param body
     * @return
     */
    private static Map<String, String> converFormBody(String body) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject json = new JSONObject(body);
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = json.getString(key);
                if (!TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 处理响应，打印日志
     *
     * @param response
     * @return
     */
    private Response handleResponse(Response response, String body) {
        StringBuilder logSb = new StringBuilder();
        try {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            logSb.append("Url:");
            logSb.append(response.request().url().toString());
            logSb.append("\n");
            logSb.append(",Body:");
            logSb.append(body);
            logSb.append("\n");
            logSb.append(",Response:");
            logSb.append(buffer.clone().readString(charset));
        } catch (Exception e) {
            e.printStackTrace();
            logSb.append("handleResponse error!");
        }
        KLog.d(logSb.toString());
        return response;
    }
}
