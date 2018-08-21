package com.chenlei.json.utils;

import org.testng.Reporter;

/**
 * Created by Administrator on 2016/5/24.
 */
public class JDBLog {
    public static void info(String TAG, String s) {
        String message = TAG + s;
        System.out.println(message);
        Reporter.log(message);
        Reporter.log("<br/>");
    }
}
