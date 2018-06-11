package com.walkud.base.lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关工具类
 */
public class NetUtil {

    /**
     * 是否为Wifi连接
     *
     * @return
     */
    public static boolean isWifiConnected() {
        NetworkInfo networkInfo = getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo == null ? false : networkInfo.isConnected();
    }

    /**
     * 是否存在网络
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        NetworkInfo networkInfo = getConnectivityManager().getActiveNetworkInfo();
        return networkInfo == null ? false : networkInfo.isAvailable();
    }

    /**
     * 获取网络连接管理
     *
     * @return
     */
    private static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) AppContextUtil.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
