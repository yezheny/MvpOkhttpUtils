/*
 * 文 件 名:  UtilDateDeserializer.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  JKF54424
 * 修改时间:  2011-11-10
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.pcjz.http.okhttp.json;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * JsonUtils转换工具类辅助类
 * 
 * @author  江天明，JKF54424
 * @version  [2011-11-10]
 */
public class UtilDateDeserializer implements JsonDeserializer<java.util.Date> {

    public java.util.Date deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
    }
}
