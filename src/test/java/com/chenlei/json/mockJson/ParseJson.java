package com.chenlei.json.mockJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.*;

/**
 * Created by linliping on 2017/7/27.
 */
public class ParseJson {

    private static ParseJson mInstance = null;
    private static FileUtil mFileUtil = null;
    private ArrayList<String> nodes = new ArrayList<String>();
    private JSONObject jsonObject = null;//即将要被mock的数据
    private static String filePath;
    private String keyPath;
    private String targetPath;
    private HashMap<String, Object> dataInArray = new HashMap<String, Object>();

    public static ParseJson getInstance(FileUtil fileUtil) {
        if (mInstance == null) {
            mInstance = new ParseJson();
            mFileUtil = fileUtil;
        }
        return mInstance;
    }

    /**
     * 对外提供的方法
     * 友情提示：每次重新mock一个新的接口，都要调用该方法初始化
     *
     * @param path 接口名称（用作文件夹名称，注意不要包含"."，如需多层路径请用"_"）
     */
    public void initNodeList(String path) {
        filePath = path;//接口名
        nodes = new ArrayList<String>();
    }

    /**
     * 对外提供的方法，mock每一个节点，输出每次mock后的结果至文件夹中
     *
     * @param node    需要被mock的节点，格式：data.clientVersion
     * @param jsonStr 需要被mock的json串
     * @return mock指定节点后的结果
     */
    public String mockNode(String node, String jsonStr) {
        //节点名：data.clientVersion
        keyPath = node;

        //创建节点文件夹：创建在接口文件夹下，以节点名命名
        targetPath = mFileUtil.mkdirFile(filePath + File.separator + keyPath);

        //mock数据
        jsonObject = new JSONObject(jsonStr);
        mockJson(node, jsonObject);
        return jsonObject.toString();
    }

    /**
     * 按照节点mock数据
     *
     * @param node 节点：格式：data.clientVersion
     * @param job  需要被mock的json
     * @return
     */
    private String mockJson(String node, JSONObject job) {
        String[] key = node.split("\\.");

        if (key.length <= 0) {
            return "";
        }
        Object obj = job.get(key[0]);
        if (obj instanceof JSONArray) {
            //array
            JSONArray array = (JSONArray) obj;
            for (int i = 0; i < array.length(); i++) {
                if (array.get(i) instanceof JSONObject) {
                    String nodeC = getNodeC(key);
                    if (node.split("\\.").length == 1) {
                        mapValue(job, key[0]);
                        break;
                    } else {
                        dataInArray.put(nodeC, obj);
                        mockJson(nodeC, (JSONObject) array.get(i));
                    }
                }
            }
        } else if (obj instanceof JSONObject) {
            //object
            String nodeC = getNodeC(key);
            if(nodeC == key[0]){
                mapValue(job, key[0]);
            } else {
                mockJson(nodeC, (JSONObject) obj);
            }
        } else {
            if(dataInArray.get(key[0]) != null) {
                mapArrayValue(dataInArray.get(key[0]), key[0]);
                dataInArray.remove(key[0]);
            } else {
                mapValue(job, key[0]);
            }
        }

        return jsonObject.toString();
    }

    /**
     * 修改指定节点数据
     *
     * @param job
     * @param key
     */
    private void mapValue(JSONObject job, String key) {
        String type = job.get(key).getClass().getSimpleName();
        System.out.println("keyPath = " + keyPath + "; type = " + type);
        HashMap<String, Object> mockData = Const.getMockData(type);
        String fileName;
        for (String s : mockData.keySet()) {
            if (mockData.get(s) == null) {
                job.put(key, JSONObject.NULL);//特殊：设置value为null
                fileName = keyPath + "_" + s;
            } else {
                job.put(key, mockData.get(s));
                fileName = keyPath + "_" + s.toString();
            }
            writeMock2File(fileName, jsonObject.toString());
        }
    }

    private void mapArrayValue(Object obj, String key) {
        if (obj instanceof JSONArray) {
            //array
            JSONArray array = (JSONArray) obj;
            HashMap<String, Object> mockData = new HashMap<String, Object>();
            if (array.get(0) instanceof JSONObject) {
                String type = ((JSONObject)array.get(0)).get(key).getClass().getSimpleName();
                mockData = Const.getMockData(type);
            }
            String fileName = "";
            for (String s : mockData.keySet()) {
                for (int i = 0; i < array.length(); i++) {
                    if (array.get(i) instanceof JSONObject) {
                        JSONObject jsonObject = (JSONObject) array.get(i);
                        if (mockData.get(s) == null) {
                            jsonObject.put(key, JSONObject.NULL);//特殊：设置value为null
                            fileName = keyPath + "_" + s;
                        } else {
                            jsonObject.put(key, mockData.get(s));
                            fileName = keyPath + "_" + s.toString();
                        }
                    }
                }
                writeMock2File(fileName, jsonObject.toString());
            }
        } else {
            return;
        }
    }

    /**
     * 将mock的最终结果写入文件中
     *
     * @param fileName   文件名
     * @param mockResult mock的最终结果
     */
    private void writeMock2File(String fileName, String mockResult) {
        //将mock的json数据输出到文件
        fileName = fileName.replace(".", "_");
        mFileUtil.writeTxt2File(targetPath, fileName + ".json", mockResult);
    }

    /**
     * 获取下一层节点
     *
     * @param key 当前节点，例如："data.jsonObjectTest.name"-->key:{"data", "jsonObjectTest", "name"}
     * @return 下一层节点，例如："jsonObjectTest.name"
     */
    private String getNodeC(String[] key) {
        if (key != null && key.length >= 2) {
            String result = "";
            int size = key.length;
            for (int i = 1; i < size; i++) {
                result += key[i];
                if (i != size - 1) result += ".";
            }
            return result;
        } else if (key == null) {
            return null;
        } else {
            return key[0];
        }
    }

    /**
     * 对外提供的方法，获取传入的json中的所有节点（除去最外层error及error包含的节点）
     *
     * @param jsonStr 整个接口内容（符合json格式的json串）
     * @return 所有节点（除去最外层error及error包含的节点），例如："data.jsonObjectTest.name"；
     */
    public ArrayList<String> getJsonNodes(String jsonStr) {
        try {
            String key;
            JSONObject job = new JSONObject(jsonStr);

            Iterator<?> it = job.keys();
            while (it.hasNext()) {
                key = (String) it.next();
                if ("error".equals(key)) {
                    continue;
                }
                System.out.printf(key);
                JSONObject obj = (JSONObject) job.get(key);
                getNodes(obj, key);
            }

            return nodes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 递归遍历指定节点下的所有节点
     *
     * @param object  指定的json串
     * @param nodePre json串对应的节点
     */
    private void getNodes(JSONObject object, String nodePre) {
        Iterator<?> it = object.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object obj = object.get(key);

            if (obj instanceof JSONArray) {
                //jsonArray
                JSONArray array = (JSONArray) obj;
                for (int i = 0; i < array.length(); i++) {
                    addNode(getKey(nodePre, key));
                    if (array.get(i) instanceof JSONObject) {
                        getNodes((JSONObject) array.get(i), getKey(nodePre, key));
                    }
                }
            } else if (obj instanceof JSONObject) {
                //jsonObject
                addNode(getKey(nodePre, key));
                getNodes((JSONObject) obj, getKey(nodePre, key));
            } else {
                addNode(getKey(nodePre, key));
            }
        }
    }

    /**
     * 将所有遍历获取到的节点，添加到list中（除去重复的节点名，以防array中节点获取重复）
     *
     * @param key
     */
    private void addNode(String key) {
        if (!nodes.contains(key)) {
            nodes.add(key);
        }
    }

    /**
     * 获取当前节点最终生成的节点名：由之前的所有节点（data.jsonObjectTest）+ "." + 当前节点名（"name"）
     *
     * @param nodePre 之前的所有节点（data.jsonObjectTest）
     * @param key     当前节点名（"name"）
     * @return
     */
    private String getKey(String nodePre, String key) {
        if (nodePre != null && nodePre.length() > 0) {
            key = nodePre + "." + key;
        }
        return key;
    }





}
