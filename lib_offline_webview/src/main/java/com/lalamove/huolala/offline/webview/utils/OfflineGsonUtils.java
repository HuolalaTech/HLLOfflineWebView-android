package com.lalamove.huolala.offline.webview.utils;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author joseph.gao
 * <p>
 * gson工具类
 * <p>
 * toJson
 * fromJson
 */
public class OfflineGsonUtils {

    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .serializeNulls() //支持序列化null的参数
                .enableComplexMapKeySerialization()//支持将序列化key为object的map,默认只能序列化key为string的map
                .create();
    }

    public static String toJson(Object obj) {
        if (null == obj) {
            return "";
        }
        try {
            return GSON.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Object fromJson(String json, Type classType) {
        if (null == json) {
            return null;
        }
        try {
            return GSON.fromJson(json, classType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        T jsonObj = null;
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                jsonObj = GSON.fromJson(jsonStr, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonObj;
    }

    public static <T> T fromJson(String jsonStr) {
        T jsonObj = null;
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                jsonObj = GSON.fromJson(jsonStr, new TypeToken<T>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonObj;
    }

    public static <T> List<T> fromJsonToList(String jsonStr, Class<T> clazz) {
        List<T> jsonObj = null;

        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                Type type = new ParameterizedTypeImpl(clazz);
                jsonObj = GSON.fromJson(jsonStr, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonObj;
    }


    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
