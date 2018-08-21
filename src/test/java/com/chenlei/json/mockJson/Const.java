package com.chenlei.json.mockJson;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by linliping on 2017/7/27.
 */
class Const {

    public static HashMap<String, Object> mockString = new HashMap<String, Object>();
    public static HashMap<String, Object> mockNumber = new HashMap<String, Object>();
    public static HashMap<String, Object> mockBool = new HashMap<String, Object>();
    public static HashMap<String, Object> mockJsonObject = new HashMap<String, Object>();
    public static HashMap<String, Object> mockJsonArray = new HashMap<String, Object>();

    public static HashMap<String, Object> getMockData(String type) {
        if("String".equals(type)) {
            return mockString;
        } else if("Integer".equals(type)) {
            return mockNumber;
        } else if("Boolean".equals(type)) {
            return mockBool;
        } else if("JSONObject".equals(type)) {
            return mockJsonObject;
        } else if ("JSONArray".equals(type)) {
            return mockJsonArray;
        }
        return null;
    }

    public static void initMockData() {
        //String
        mockString.put("empty", "");
        mockString.put("null", null);
        mockString.put("longValue", "asdcfvfdgjfkle多发几点睡觉的👩👨👴🌂👵~!@#$%^&*(jdsal;fdjdf就哦但是你发的那份但凡 i 低温气候奶粉的骚女第五期南方电网发的撒打算发 豆腐哈捏窝囊废   发的那份 i 额外前锋 i 不去凤凰dishnfidnwqbkfriewobfnreu");

        //Number
        mockNumber.put("0", 0);
        mockNumber.put("-100", -100);
        mockNumber.put("1", 1);
        mockNumber.put("100", 100);
        mockNumber.put("null", null);
        mockNumber.put("bigNum", 999999999);

        //Bool
        mockBool.put("true", true);
        mockBool.put("false", false);
        mockBool.put("null", null);

        //JsonObject
        mockJsonObject.put("empty", new JSONObject());
        mockJsonObject.put("null", null);
        mockJsonObject.put("123", 123);
        mockJsonObject.put("abcd", "abcd");

        //JsonArray
        Object[] o = {};
        mockJsonArray.put("empty", o);
        mockJsonArray.put("null", null);
        mockJsonArray.put("123", 123);
        mockJsonArray.put("abcd", "abcd");


    }
}
