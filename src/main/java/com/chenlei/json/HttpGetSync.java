package com.chenlei.json;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by chenlei on 2017/8/24.
 */
public class HttpGetSync {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Request request, IOException e) {

            }

            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                System.out.println(string);
                response.body().close();
            }
        });
    }
}
