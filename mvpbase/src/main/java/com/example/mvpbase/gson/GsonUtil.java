package com.example.mvpbase.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author: 雄厚
 * Date: 2020/8/10
 * Time: 17:34
 */
public class GsonUtil {

    private GsonUtil() {
    }
    private static Gson getGsonObject() {
        return new GsonBuilder().serializeNulls().create();
    }

    /**
     * 对象转Gson字符串
     *
     * @param object
     * @return
     */
    public static <T extends Object> String ser(T object) {
        Gson gson = getGsonObject();
        return gson.toJson(object);
    }
    /**
     * Gson字符串转可序列化对象
     *
     * @param object
     * @param clazz
     * @return
     */
    public static <T extends Object> T deer(String object, Class<T> clazz) {
        Gson gson = getGsonObject();

        T result = null;
        try {
            result = gson.fromJson(object, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
