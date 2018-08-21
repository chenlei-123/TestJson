package com.chenlei.json;

import com.chenlei.json.component.HelperClassException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by chenlei on 2017/5/10.
 */
public class TestStart {
    public static String url = "http://100.73.21.5:8080/openApi/front/getApiResponse";
    public static String reqPara1 = "secretKey";
    public static String reqPara2 = "apiUrl";
    public static String value1 = "FRONT_20170814";
    public static String value2 = "/test/333";

    public static void main(String[] args) throws IOException, HelperClassException {
        getApidocData();

        new TestGson().generateData();
        new TestGson().wrongData();
    }

    public static void getApidocData() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        //设置媒体类型。application/json表示传递的是一个json格式的对象
        MediaType mediaType = MediaType.parse("application/json");
        //使用JSONObject封装参数
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(reqPara1,value1);
        jsonObject.addProperty(reqPara2,value2);

        //创建RequestBody对象，将参数按照指定的MediaType封装
        RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
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
                .getAsJsonObject()
                .toString();

        String property = System.getProperty("user.dir");
        File file = new File(property);
        File file1 = new File(file + "/test.json");
        FileUtils.writeStringToFile(file1,jsonData);
    }
}
