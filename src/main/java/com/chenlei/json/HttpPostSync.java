package com.chenlei.json;

import com.google.gson.JsonObject;
import com.squareup.okhttp.*;

import java.io.IOException;

/**
 * Created by chenlei on 2017/8/24.
 */
public class HttpPostSync {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        //使用JSONObject封装参数
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("secretKey","FRONT_20170814");
        jsonObject.addProperty("apiUrl","/test/333");
        //设置媒体类型。application/json表示传递的是一个json格式的对象
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder().post(requestBody).url("http://100.73.21.5:8080/openApi/front/getApiResponse").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Request request, IOException e) {

            }

            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                System.out.println(result);
                response.body().close();
            }
        });
    }
}
