/*
 * 文 件 名:  SQLDateDeserializer.java
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
 * List<String>特殊解析
 * @author jiangtianming
 */
public class ListStringDeserializer implements JsonDeserializer<String> {

    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null){
            return null;
        }
        if (json.isJsonPrimitive()){
            return new String(json.getAsJsonPrimitive().getAsString());
        } else if (json.isJsonArray()){
            return new String(json.getAsJsonArray().toString());
        } else if (json.isJsonObject()){
            return new String(json.getAsJsonObject().toString());
        }
        return json.toString();
    }
}
