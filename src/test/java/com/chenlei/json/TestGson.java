package com.chenlei.json;

import com.chenlei.json.component.HelperClassException;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by chenlei on 2017/5/8.
 */

class TestGson {
    private String[] beanList;//存储javabean类的list
    private ArrayList<String> types;//type存储假数据类型，值为null、array、object等
    private ArrayList<String> firstNodes;
    private ArrayList<String> nodes;//将json存储的javabean中的类遍历一次，转成所有节点存储在nodes的list中
    private ArrayList<String> secondNodes;//二级字段节点
    private String jsonFileName = "/test.json";

    private String handleBeanFile(String fileName) {
        String substring = fileName.substring(0, fileName.length() - 5);
        String behind = substring.substring(1);
        String before = substring.substring(0, 1);
        String s = before.toLowerCase();
        return s + behind;
    }

    void generateData() throws IOException, HelperClassException {

        String rootPath = System.getProperty("user.dir");
        String javaBeanPath = "/src/test/java/com/rrh/json/bean/";

        nodes = new ArrayList<String>();//将json存储的javabean中的类遍历一次，转成所有节点存储在nodes的list中
        File bean = new File(rootPath + javaBeanPath);
        File beanFiles = new File(bean.toString());
        beanList = beanFiles.list();
        if (beanList.length == 0) {
            throw new HelperClassException("bean is empty");
        }
        //从json的javabean中遍历一级字段
        //从list中删除掉Root、Data、Error
        for (String beanFile : beanList) {
            if ("Root.java".equals(beanFile) || "Data.java".equals(beanFile) || "Error.java".equals(beanFile)) {
                continue;
            }
            String node = handleBeanFile(beanFile);
            nodes.add(node);
        }

        File file = new File(rootPath + jsonFileName);
        String jsonStr = FileUtils.readFileToString(file);

        //=========================
        //data字段数据准备
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jsonStr);
        //list存储假数据类型，值为null、array、object等
        types = new ArrayList<String>();
        types.add("null");
        types.add("array");
        types.add("object");
        //给json结构最外层data字段
        for (String type : types) {
            if (type.equals("null")) {
                jsonTree.getAsJsonObject().add("data", null);
            } else if (type.equals("array")) {
                JsonArray jsonArray = new JsonArray();
                jsonTree.getAsJsonObject().add("data", jsonArray);
            } else if (type.equals("object")) {
                JsonObject jsonObject = new JsonObject();
                jsonTree.getAsJsonObject().add("data", jsonObject);
            }
            File file1 = new File(rootPath + "/tmp/data/data" + type + ".json");
            FileUtils.writeStringToFile(file1, jsonTree.toString());
        }

        //=========================
        //data字段里面一级字段数据准备数据准备

        JsonElement jsonTree1 = parser.parse(jsonStr);
        JsonElement dataObj = jsonTree1.getAsJsonObject().get("data");
        JsonObject data = null;
        if (dataObj.isJsonArray()) {
            /**
             * 有待完善
             */
            return;
        } else if (dataObj.isJsonObject()) {
            data = dataObj.getAsJsonObject();
        }

        firstNodes = new ArrayList<String>();
        for (String node : nodes) {
            if (data.has(node)) {//如果data中包含nodes的list中存储的从javabean中遍历存储的节点，就存储在一级节点的firstNodes中
                firstNodes.add(node);
            }
        }

        //data字段里面一级字段中值为object、array的字段
        for (String firstNode : firstNodes) {
            for (String type : types) {
                if (type.equals("null")) {
                    data.add(firstNode, null);
                } else if (type.equals("array")) {
                    JsonArray jsonArray = new JsonArray();
                    data.add(firstNode, jsonArray);
                } else if (type.equals("object")) {
                    JsonObject jsonObject = new JsonObject();
                    data.add(firstNode, jsonObject);
                }
                File file1 = new File(rootPath + "/tmp/data/" + firstNode + type + ".json");
                FileUtils.writeStringToFile(file1, jsonTree1.toString());
            }
        }

        //=========================
        //二级字段数据准备数据准备
        secondNodes = new ArrayList<String>();
        for (String node : nodes) {
            if (!firstNodes.contains(node)) {
                secondNodes.add(node);
            }
        }
        for (String secondNode : secondNodes) {
            System.out.println("secondNode:" + secondNode);
        }

        if (!secondNodes.isEmpty()) {
            //二级字段不为空
            generateSecondData();

        } else {
            //数据准备完成
            System.out.println("数据准备完成");
        }
    }

    private void generateSecondData() throws IOException {
        String rootPath = System.getProperty("user.dir");
        File file = new File(rootPath + jsonFileName);
        String jsonStr = FileUtils.readFileToString(file);
        //=========================
        //data字段数据准备
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jsonStr);
        JsonObject data = jsonTree.getAsJsonObject().getAsJsonObject("data");

        for (String firstNode : firstNodes) {
            JsonElement jsonElement = data.get(firstNode);
            if (jsonElement.isJsonObject()) {
                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                for (String secondNode : secondNodes) {
                    if (jsonElement.getAsJsonObject().has(secondNode)) {

                        for (String type : types) {
                            if (type.equals("null")) {
                                asJsonObject.add(secondNode, null);
                            } else if (type.equals("array")) {
                                JsonArray jsonArray = new JsonArray();
                                asJsonObject.add(secondNode, jsonArray);
                            } else if (type.equals("object")) {
                                JsonObject jsonObject = new JsonObject();
                                asJsonObject.add(secondNode, jsonObject);
                            }
                            File file1 = new File(rootPath + "/tmp/data/" + secondNode + type + ".json");
                            FileUtils.writeStringToFile(file1, jsonTree.toString());
                        }
                    }
                }

            } else if (jsonElement.isJsonArray()) {
                JsonObject arrayObject = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
                for (String secondNode : secondNodes) {
                    if (arrayObject.has(secondNode)) {

                        for (String type : types) {
                            if (type.equals("null")) {
                                arrayObject.add(secondNode, null);
                            } else if (type.equals("array")) {
                                JsonArray jsonArray = new JsonArray();
                                arrayObject.add(secondNode, jsonArray);
                            } else if (type.equals("object")) {
                                JsonObject jsonObject = new JsonObject();
                                arrayObject.add(secondNode, jsonObject);
                            }
                            File file1 = new File(rootPath + "/tmp/data/" + secondNode + type + ".json");
                            FileUtils.writeStringToFile(file1, jsonTree.toString());
                        }

                    }

                }

            }
        }
    }


    void wrongData() throws IOException {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        String rootPath = System.getProperty("user.dir");
        File file = new File(rootPath + jsonFileName);
        String jsonStr = FileUtils.readFileToString(file);

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jsonStr);

        TestGson testGson = new TestGson();

        ArrayList<String> types = new ArrayList<String>();
        types.add("bignum");
        types.add("bool");
        types.add("empty");
        types.add("null");
        types.add("zero");

        for (String type : types) {
            System.out.println("批量生成数值为[" + type + "]的数据");

            HashMap<String, String> stringHashMap = testGson.analysisJson(jsonTree, hashMap);

            for (Map.Entry<String, String> entry : stringHashMap.entrySet()) {

                List<String> strings = FileUtils.readLines(file);

                ArrayList<String> newString = new ArrayList<String>();

                for (String string : strings) {
                    if (string.contains(entry.getKey()) && string.contains(",")) {
                        if ("bignum".equals(type) && "Number".equals(entry.getValue())) {
                            string = "\"" + entry.getKey() + "\"" + ":1234567890,";
                        } else if ("bool".equals(type) && "Boolean".equals(entry.getValue())) {
                            string = "\"" + entry.getKey() + "\"" + ":false,";
                        } else if ("empty".equals(type) && "String".equals(entry.getValue())) {
                            string = "\"" + entry.getKey() + "\"" + ":" + "\"\",";
                        } else if ("null".equals(type)) {
                            string = "\"" + entry.getKey() + "\"" + ":" + "null,";
                        } else if ("zero".equals(type) && "Number".equals(entry.getValue())) {
                            string = "\"" + entry.getKey() + "\"" + ":0,";
                        }
                    } else if (string.contains(entry.getKey())) {
                        if ("bignum".equals(type) && "Number".equals(entry.getValue())) {
                            string = "\"" + entry.getKey() + "\"" + ":1234567890";
                        } else if ("bool".equals(type) && "Boolean".equals(entry.getValue())) {
                            string = "\"" + entry.getKey() + "\"" + ":false";
                        } else if ("empty".equals(type) && "String".equals(entry.getValue())) {
                            string = "\"" + entry.getKey() + "\"" + ":" + "\"\"";
                        } else if ("null".equals(type)) {
                            string = "\"" + entry.getKey() + "\"" + ":" + "null";
                        } else if ("zero".equals(type) && "Number".equals(entry.getValue())) {
                            string = "\"" + entry.getKey() + "\"" + ":0";
                        }
                    }
                    newString.add(string);
                }
                if (!newString.equals(strings)) {
                    File file1 = new File(rootPath + "/tmp/" + type + "/" + entry.getKey() + ".json");
                    FileUtils.writeLines(file1, newString);
                }
            }

        }


    }

    private String getValueType(String value) {
        String type;
        if (value.equalsIgnoreCase("null")) {
            return "null";
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            type = "Boolean";
        } else {
            try {
                JavaTypesHelper.toDouble(value, 0.0);
                type = "Number";
            } catch (Exception e) {
                type = "String";
            }
        }
        return type;
    }


    @SuppressWarnings("rawtypes")
    private HashMap<String, String> analysisJson(JsonElement jsonElement, HashMap<String, String> hashMap) {
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                analysisJson(jsonArray.get(i), hashMap);
            }

        } else if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String key = entry.getKey();
                JsonElement element = jsonObject.get(key);
                if (element.isJsonArray()) {
                    JsonArray asJsonArray = element.getAsJsonArray();
                    analysisJson(asJsonArray, hashMap);
                } else if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    analysisJson(object, hashMap);
                } else if (element.isJsonPrimitive()) {
                    String value = entry.getValue().toString();
                    String valueType = getValueType(value);
                    if (valueType.equals("Boolean")) {
                        hashMap.put(key, "Boolean");

                    } else if (valueType.equals("Number")) {
                        hashMap.put(key, "Number");

                    } else if (valueType.equals("String")) {
                        hashMap.put(key, "String");
                    }
                }
            }
        }
        return hashMap;

    }
}
