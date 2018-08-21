package com.chenlei.json.mockJson;

import com.chenlei.json.JsonFormatTool;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linliping on 2017/7/3.
 */
public class ParseJson2MockFile {
    public static String url = "http://apiserver.jdb-dev.com/openApi/front/getApiResponse";
    public static String reqPara1 = "secretKey";
    public static String reqPara2 = "apiUrl";
    public static String value1 = "FRONT_20170814";
    /**
     * 请求的接口名
     */
    public static String value2 = "/mybankv21/phptradeui/borrowing/getBorrowInfo";


    private static ParseJson mParseJson = null;
    private static FileUtil mFileUtil = null;
    private static String jsonText = "";//从文件读取出来后，即将要被mock的数据
    private static String filePath = "";//mock以后文件存储的地址："tmp/tmp"
    private static List<String> nodes = null;//存储所有需要mock的字段
    private static JsonFormatTool formatTool = new JsonFormatTool();

    public static void main(String[] args) throws IOException {
        getApidocData();

        //初始化工作
        initInstance();

        mParseJson.initNodeList(filePath + File.separator);
        //获取需要mock的所有字段及该字段对应的类型
        nodes = mParseJson.getJsonNodes(jsonText);

        for (int i = 0; i < nodes.size(); i++) {
            //遍历所有节点，mock数据
            mParseJson.mockNode(nodes.get(i), jsonText);
        }
        String projectPath = System.getProperty("user.dir");
        File file = new File(projectPath + "/tmp/data/unJson/unJson.json");
        String unJsonStr = "{\"data\": ,\"error\": {\"returnCode\": 4,\"returnUserMessage\": \"test string\",\"returnMessage\": \"test string\"}}";
        String jsonStr = formatTool.formatJson(unJsonStr);
        FileUtils.writeStringToFile(file, jsonStr);
    }

    private static void initInstance() {
        //准备mock的数据
        Const.initMockData();

        //初始化FileUtil、从文件读取即将要被mock的数据
        mFileUtil = FileUtil.getInstance();
        try {
            File file = new File("test.json");
            jsonText = mFileUtil.readTxtFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化mock文件路径
        filePath = "tmp/data";

        //初始化mock类
        mParseJson = ParseJson.getInstance(mFileUtil);
        nodes = new ArrayList<String>();
    }

    public static void getApidocData() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        //设置媒体类型。application/json表示传递的是一个json格式的对象
        MediaType mediaType = MediaType.parse("application/json");
        //使用JSONObject封装参数
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(reqPara1, value1);
        jsonObject.addProperty(reqPara2, value2);

        //创建RequestBody对象，将参数按照指定的MediaType封装
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder().post(requestBody).url(url).build();
        String result = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            result = response.body().string();
            System.out.println(result);
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser jsonParser = new JsonParser();
        JsonElement parse = jsonParser.parse(result);
        String jsonData = parse
                .getAsJsonObject().get("data")
                .getAsJsonObject().get("apiResponse")
                .getAsJsonArray().get(0)
                .getAsJsonObject().get("responseData")
                .toString();

        String formatJson = formatTool.formatJson(jsonData);

        String property = System.getProperty("user.dir");
        File file = new File(property);
        File file1 = new File(file + "/test.json");
        FileUtils.writeStringToFile(file1, formatJson);

    }

}
