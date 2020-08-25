package com.example.mvpbase.json;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Json数据工具类
 * @author 雄厚
 */
public final class JsonUtil {

    private static Gson gson;

    private JsonUtil() {
        // 这个类不能实例化
    }

    /**
     * 单例模式
     */
    private static Gson getInstance() {
        if (null == gson) {
            synchronized (JsonUtil.class) {
                if (null == gson) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    /**
     * Json -> Bean
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return JsonUtil.getInstance().fromJson(json, classOfT);
    }

    /**
     * Json -> new TypeToken<List<User>>(){}.getType()
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return JsonUtil.getInstance().fromJson(json, typeOfT);
    }

    /**
     * Bean -> Json
     */
    public static String toJson(Object src) {
        return JsonUtil.getInstance().toJson(src);
    }

}