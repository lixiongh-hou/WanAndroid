package com.example.mvpbase.utils.system;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.mvpbase.base.BaseApp;
import com.example.mvpbase.gson.GsonUtil;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * @author: 雄厚
 * Date: 2020/8/10
 * Time: 17:29
 * SharedPreferences保存数据的工具类
 */
public class SPUtil {
    private SPUtil() {
        // 这个类不能实例化
    }
    private static SharedPreferences mSP = null;

    private static SharedPreferences getSP() {
        if (null == mSP) {
            Context context = BaseApp.getAppContext();
            mSP = context.getSharedPreferences(ConstantUtil.APP_NAME, Context.MODE_PRIVATE);
        }
        return mSP;
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void put(String key, Object value) {

        if (null == value) {
            return;
        }

        SharedPreferences.Editor editor = SPUtil.getSP().edit();

        // 判断数据类型
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }

        SPCompat.apply(editor);
    }

    /**
     * @param key      键
     * @param defValue 默认值
     * @return 取出的数据
     */
    public static Object get(String key, @NonNull Object defValue) {

        SharedPreferences sp = SPUtil.getSP();

        // 判断数据类型
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }

        return null;
    }
    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public static <T> boolean putListData(String key, LinkedList<T> list) {
        boolean result;
        SharedPreferences.Editor editor = SPUtil.getSP().edit();
        JsonArray array = new JsonArray();
        if (list.size() <= 0) {
            editor.putString(key, array.toString());
            editor.apply();
            return false;
        }
        String type = list.get(0).getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public static <T> LinkedList<T> getListData(String key, Class<T> cls) {
        SharedPreferences sp = SPUtil.getSP();
        LinkedList<T> list = new LinkedList<>();
        String json = sp.getString(key, "");
        if (!"".equals(json) && json.length() > 0) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }
    /**
     * 以下是保存类的方式，跟上面的FILE_NAME表不在一个表里
     */
    public static <T extends Serializable> boolean putByClass(String key, T entity) {
        if (entity == null) {
            return false;
        }
        SharedPreferences.Editor et = SPUtil.getSP().edit();
        String json = GsonUtil.ser(entity);
        et.putString(key, json);
        return et.commit();
    }

    /**
     * 获取以类名为表名的，某个key值上的value
     * 第二个参数是，类名class,也就是当前的表名
     */
    public static <T extends Serializable> T getByClass(String key, Class<T> clazz) {
        SharedPreferences sp = SPUtil.getSP();
        String json = sp.getString(key, null);
        if (json == null) {
            return null;
        }
        return GsonUtil.deer(json, clazz);
    }

    /**
     * 移除某个key对应的值
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = SPUtil.getSP().edit();
        editor.remove(key);
        SPCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        SharedPreferences.Editor editor = SPUtil.getSP().edit();
        editor.clear();
        SPCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SPCompat {

        private static final Method M_APPLY_METHOD = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class cls = SharedPreferences.Editor.class;
                return cls.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行,否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            if (null != M_APPLY_METHOD) {
                try {
                    M_APPLY_METHOD.invoke(editor);
                } catch (IllegalArgumentException |
                        IllegalAccessException |
                        InvocationTargetException e) {
                    e.printStackTrace();
                }
                return;
            }
            editor.commit();
        }
    }




}
