package com.chenlei.json;


import com.google.gson.JsonObject;
import com.squareup.okhttp.*;
//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * Created by chenlei on 2017/8/24.
 */
public class HttpPost {
    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        //设置媒体类型。application/json表示传递的是一个json格式的对象
        MediaType mediaType = MediaType.parse("application/json");
        //使用JSONObject封装参数
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("secretKey","FRONT_20170814");
        jsonObject.addProperty("apiUrl","/mybankv21/phpfriend/friend/follow/getFansList");

        //创建RequestBody对象，将参数按照指定的MediaType封装
        RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
        Request request = new Request.Builder().post(requestBody).url("http://apiserver.jdb-dev.com/openApi/front/getApiResponse").build();
        String result = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            result = response.body().string();
            System.out.println(result);
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonFormatTool formatTool = new JsonFormatTool();
        String json = formatTool.formatJson(result);
        System.out.println(json);
//        String property = System.getProperty("user.dir");
//        File file = new File(property);
//        File file1 = new File(file + "/test.json");
//        FileUtils.writeStringToFile(file1,result);

    }

}
