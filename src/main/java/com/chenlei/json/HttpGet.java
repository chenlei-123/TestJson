package com.chenlei.json;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by chenlei on 2017/8/24.
 */
public class HttpGet {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if(response.code() == 200){
                String string = response.body().string();
                System.out.println(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response.body()!=null){
                try {
                    response.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
