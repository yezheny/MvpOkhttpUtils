package com.pcjz.http.okhttp.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pcjz.http.okhttp.exception.FromJsonToBeanFailException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;


/**
 * JSON转换类
 *
 * @author 江天明
 */
public class JsonUtils {

    public static JsonElement getJsonElement(Object bean) {
        return getGson().toJsonTree(bean);
    }

    /**
     * 将BEAN转换成JSON字符串
     *
     * @param bean BEAN
     * @return JSON字符串
     */
    public static String bean2Json(Object bean) {
        return getGson().toJson(bean);
    }

    /**
     * 根据泛型类型获取参数化类型
     *
     * @param raw
     * @param args
     * @return
     */
    public static ParameterizedType getParameterizedType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * json转换成带泛型属性bean
     *
     * @param json
     * @param raw  对象类型
     * @param args 泛型类型
     * @param <T>
     * @return
     * @throws FromJsonToBeanFailException
     */
    public static <T> T json2Bean(String json, Class raw, Type... args) throws FromJsonToBeanFailException {
        Gson gson = getGson();
        T obj = null;
        try {
            if (args == null || args.length == 0) {
                obj = (T) gson.fromJson(json, raw);
            } else {
                obj = gson.fromJson(json, getParameterizedType(raw, args));
            }
        } catch (JsonParseException exc) {
            throw new FromJsonToBeanFailException(exc.getMessage());
        }
        return obj;
    }

    /**
     * 构建公共GSON对象
     *
     * @return
     */
    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(java.sql.Date.class, new SQLDateDeserializer())
                .registerTypeAdapter(java.util.Date.class, new UtilDateDeserializer())
                .registerTypeAdapter(String.class, new ListStringDeserializer())
                .create();
    }

    /**
     * 将JSON字符串转换成BEAN，如果JSON字符串中包含有Bean里不存在的字段，则不存在的字段会被忽略
     *
     * @param json JSON字符串
     * @param type BEAN的类型
     * @return BEAN对象
     */
    public static <T> T json2bean(String json, Type type) {
        Gson gson = new GsonBuilder().registerTypeAdapter(java.sql.Date.class,
                new SQLDateDeserializer()).registerTypeAdapter(
                java.util.Date.class, new UtilDateDeserializer())
                .setDateFormat(DateFormat.LONG).create();
        return gson.fromJson(json, type);
    }
}