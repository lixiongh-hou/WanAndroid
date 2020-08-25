package com.example.mvpbase.networks;

import androidx.annotation.NonNull;

/**
 * Created by leo
 * on 2019/7/29.
 *
 */
public enum NetworkType {
    /**wifi*/
    NETWORK_WIFI("wifi"),
    /**4G*/
    NETWORK_4G("4G"),
    /**3G*/
    NETWORK_3G("3G"),
    /**2G*/
    NETWORK_2G("2G"),
    /**未知网络*/
    NETWORK_UNKNOWN("net_unknow"),
    //没有网络
    NETWORK_NO("no_network");


    private String desc;
    NetworkType(String desc) {
        this.desc = desc;
    }

    @NonNull
    @Override
    public String toString() {
        return desc;
    }
}
