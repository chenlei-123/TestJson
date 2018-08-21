package com.chenlei.json.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/2/20.
 */
public class ProxyServerForMac extends Thread{
    private String Case;
    private String rulePath;

    public ProxyServerForMac(String rulePath,String Case) {
        this.Case = Case;
        this.rulePath = rulePath;
    }

    public void run() {
        String rule = rulePath+"rule_mock.js";
        System.out.println(getClass().getSimpleName()+rule);
        String line;
        String startProxyStr = null;
        try {
            Process exec = Runtime.getRuntime().exec("anyproxy -r"+" "+rule+" "+Case);
            InputStream inputStream = exec.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            startProxyStr = stringBuilder.toString();
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            exec.destroy();
            System.out.println(startProxyStr);
        } catch (IOException e) {

        }
    }
}
