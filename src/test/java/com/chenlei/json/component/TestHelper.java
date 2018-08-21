package com.chenlei.json.component;

import com.chenlei.json.utils.JDBLog;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class TestHelper {

    /**
     * 等待app启动
     * @param driver
     * @param TAG
     * @throws InterruptedException
     */
    public static void waitForAppStart(AndroidDriver driver,String TAG) throws InterruptedException {
        String mainTabActivity = ".newmodule.tab.maintab.MainTabFragmentActivity";
        int waitTimes = 0;
        while (true) {
            String activity = driver.currentActivity();
            if (mainTabActivity.equals(activity)) {
                JDBLog.info(TAG, "应用打开");
                break;
            }
            Thread.sleep(1000);
            JDBLog.info(TAG, "等待应用打开");
            waitTimes++;
            if (waitTimes > 20) {
                break;
            }
        }
    }

    /**
     * 读取json文件数据
     * @param packageFolder
     * @param fileName
     * @return
     */
    public static String getJsonData(String packageFolder,String fileName){
        String projectRootPathStr = System.getProperty("user.dir");
        File projectRootPath = new File(projectRootPathStr);
        File jsonDataDir = new File(projectRootPath, "json-data");
        File packageDir = new File(jsonDataDir, packageFolder);
        File file = new File(packageDir.toString());
        String jsonFileName = "";
        String[] fileNameList = file.list();
        for (String name : fileNameList) {
            if(name.equals(fileName)){
                jsonFileName = fileName;
            }
        }
        String systemName = System.getProperty("os.name");
        String completePath;
        if(systemName.equals("Mac OS X")){
            completePath = packageDir.toString()+"//"+jsonFileName;
        }else {
            completePath = packageDir.toString()+"\\"+jsonFileName;
        }

        File completeFilePath = new File(completePath);
        String jsonFileStr = "";
        try {
            jsonFileStr = FileUtils.readFileToString(completeFilePath, "utf-8");
        } catch (IOException e) {
        }
        return jsonFileStr;
    }

    /**
     * 清除借贷宝应用缓存
     */
    public static void clearAppData(){
        try {
            Runtime.getRuntime().exec("adb shell pm clear com.rrh.jdb");
        } catch (IOException e) {
            Assert.fail("io异常" + e);
        }
    }

    /**
     * 杀掉top进程
     */
    public static void killTopProcess() throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process exec = null;
        String adbCmd = "adb shell ps | grep top";
        //存储一行
        String line = null;
        //存储所有命令行输出信息
        String topStr = null;
        //top信息的那一行
        String topMessage = null;

        //杀掉top进程
        try {
            exec = runtime.exec(adbCmd);
            //取得命令结果的输出流
            InputStream fis = exec.getInputStream();
            //用一个读输出流类去读
            InputStreamReader isr = new InputStreamReader(fis);
            //用缓冲器读行
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            topStr = sb.toString();
            br.close();
            isr.close();
            fis.close();
            exec.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //过滤出用户未shell的那一行top进程信息
        ArrayList<String> topLines = null;
        if (topStr != null && !("".equals(topStr))) {
            String[] split = topStr.split("\\n");
            topLines = new ArrayList<String>();
            for (String s : split) {
                if (s.equals("")) {
                    continue;
                }
                topLines.add(s);
            }
        }
        for (String topLine : topLines) {
            if (topLine.contains("shell")) {
                topMessage = topLine;
                break;
            }
        }
        //查找top进程的进程号
        ArrayList<String> topProcess = null;
        if (topMessage != null && !("".equals(topMessage))) {
            String[] split = topMessage.split(" ");
            topProcess = new ArrayList<String>();
            for (String s : split) {
                if(s.equals("")){
                    continue;
                }
                topProcess.add(s);
            }
        }
        //执行kill命令杀掉top进程
        if(topProcess!=null){
            try {
                Runtime.getRuntime().exec("adb shell kill " + topProcess.get(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //延时必须有
        Thread.sleep(2000);

    }

    /**
     * 删除本地工程的性能文件
     * @param fileName
     */
    public static void deleteFileFromProject(String fileName){
        String root;
        String logPath;
        root = System.getProperty("user.dir");
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")) {
            logPath = "//performance//log";
        } else {
            logPath = "\\performance\\log";
        }
        //判断当前工程\performance\log目录下该Case的性能日志文件是否存在，如果存在，执行删除
        File file = new File(root + logPath +"\\"+fileName + ".txt");
        boolean delete = file.delete();
        System.out.println(delete);
    }

    /**
     * 将存在手机sd卡中的性能文件pull到本工程中
     * @param TAG
     */
    public static void pullFileFromSdcardToProject(String TAG) throws InterruptedException {
        String root = System.getProperty("user.dir");
        String logPath = null;
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")) {
            logPath = "//performance//log";
        } else {
            logPath = "\\performance\\log";
        }
        System.out.println(root + logPath);
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("adb pull data/local/tmp/" + TAG + ".txt" + " " + root + logPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //延时必须有
        Thread.sleep(1000);
    }

    /**
     * 删除手机SDka中的性能文件
     * @param TAG
     */
    public static void deleteFileFromSdcard(String TAG) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("adb shell rm data/local/tmp" + "/" + TAG + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //延时必须有
        Thread.sleep(3000);
    }

    /**
     * 杀掉top进程
     * @param TAG
     */
    public static void startTopProcess(String TAG){
        //启动top
        Runtime runtime = Runtime.getRuntime();
        try {
            //启动top日志监控，存储到手机data/local/tmp目录下
            runtime.exec("adb shell top -d 1| grep " + "com.rrh.jdb$" + " >data/local/tmp/" + TAG + ".txt");
        } catch (IOException e) {
        }
    }

    /**
     * dump cpu和内存信息
     * @param driver
     */
    public static void dumpData(AndroidDriver driver,File file) throws IOException {
        String activity = driver.currentActivity();
        Runtime runtime = Runtime.getRuntime();
        String line;
        String topDataStr = null;
        try {
            Process exec = runtime.exec("adb shell top -d 0.1 -n 1 |grep com.rrh.jdb$");
            InputStream inputStream = exec.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            topDataStr = stringBuilder.toString();
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            exec.destroy();
        } catch (IOException e) {
        }
        List<String> list = null;
        if (topDataStr != null && !("".equals(topDataStr))) {
            String[] split = topDataStr.split(" ");
            list = new ArrayList<String>();
            for (String s : split) {
                if (s.equals("")) {
                    continue;
                }
                list.add(s);
            }
            if(!list.isEmpty()){
                JDBLog.info("","当前Activity："+activity);
                JDBLog.info("","CPU信息："+list.get(2));
                JDBLog.info("","内存信息："+list.get(6));
                JDBLog.info("","当前时间："+getTime());
                String cpu = handleCPU(list.get(2));
                String memory = handleMemory(list.get(6));
                String time = getTime();
                FileUtils.writeStringToFile(file,cpu+" "+memory+" "+activity+" "+time+"\r\n",true);
            }
        }
    }
    private static String getTime(){
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return dateFormat.format(date);
    }

    /**
     * 处理cpu值，去掉%
     * @param value
     * @return
     */
    private static String handleCPU(String value){
        int i = value.indexOf("%");
        return value.substring(0, i);
    }

    /**
     * 处理内存值，去掉K
     * @param value
     * @return
     */
    private static String handleMemory(String value){
        int k = value.indexOf("K");
        return value.substring(0,k);
    }

    public static void getAppVersionNum(File file) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String line;
        String dumpDataStr = null;
        try {
            Process exec = runtime.exec("adb shell dumpsys package com.rrh.jdb");
            InputStream inputStream = exec.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            dumpDataStr = stringBuilder.toString();
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            exec.destroy();
        } catch (IOException e) {
        }
        List<String> list = null;
        if(dumpDataStr!=null && !("".equals(dumpDataStr))){
            String[] split = dumpDataStr.split(" ");
            list = new ArrayList<String>();
            for (String s : split) {
                if (s.equals("")) {
                    continue;
                }
                list.add(s);
            }
        }
        String versionNum = null;
        for (String element : list) {
            if(element.contains("versionName")){
                versionNum = element;
                break;
            }
        }
        FileUtils.writeStringToFile(file,versionNum+"\r\n",true);
    }
    /**
     * 通过Case启动代理服务器
     */
    public static void startProxyServer(String Case) {
        String projectRootPath = System.getProperty("user.dir");
        File projectRoot = new File(projectRootPath);
        File proxymock = new File(projectRoot, "proxymock");
        System.out.println(proxymock + "/" + Case + "/rule_mock.js");
        String line;
        String startProxyStr = null;
        try {
            Process exec = Runtime.getRuntime().exec("anyproxy -i --rule " + proxymock + "case1/rule_mock.js");
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
    public static void killProxyServer(){
        Runtime runtime = Runtime.getRuntime();
        Process exec = null;
        String[] shellCmd = {"/bin/sh","-c","ps -ef |grep anyproxy"};
        //存储一行
        String line = null;
        //存储所有命令行输出信息
        String psStr = null;
        //top信息的那一行
        String proxyMessage = null;

        //杀掉top进程
        try {
            exec = runtime.exec(shellCmd);
            //取得命令结果的输出流
            InputStream fis = exec.getInputStream();

//            用一个读输出流类去读
            InputStreamReader isr = new InputStreamReader(fis);
            //用缓冲器读行
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            psStr = sb.toString();
            System.out.println(psStr);
            br.close();
            isr.close();


            fis.close();
            exec.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //过滤出用户未shell的那一行top进程信息
        ArrayList<String> topLines = null;
        if (psStr != null && !("".equals(psStr))) {
            String[] split = psStr.split("\\n");
            topLines = new ArrayList<String>();
            for (String s : split) {
                if (s.equals("")) {
                    continue;
                }
                topLines.add(s);
            }
        }
        for (String topLine : topLines) {
            if (topLine.contains("-r")) {
                proxyMessage = topLine;
                break;
            }
        }
        System.out.println(proxyMessage);

        //查找anyProxy进程的进程号
        ArrayList<String> anyProxyProcess = null;
        if (proxyMessage != null && !("".equals(proxyMessage))) {
            String[] split = proxyMessage.split(" ");
            anyProxyProcess = new ArrayList<String>();
            for (String s : split) {
                if(s.equals("")){
                    continue;
                }
                anyProxyProcess.add(s);
            }
        }

        if(anyProxyProcess!=null){
            try {
                Runtime.getRuntime().exec("kill "+anyProxyProcess.get(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void killProxyServerForWindows(){
        Runtime runtime = Runtime.getRuntime();
        Process exec;
        String rootPath = System.getProperty("user.dir");
        File batPath = new File(rootPath, "bat\\KillProxyServer.bat");
        String[] shellCmd = {batPath.toString()};
        //存储一行
        String line = null;
        //存储所有命令行输出信息
        String psStr = null;
        //top信息的那一行
        String proxyMessage = null;
        //杀掉top进程
        try {
            exec = runtime.exec(shellCmd);
            //取得命令结果的输出流
            InputStream fis = exec.getInputStream();
//            用一个读输出流类去读
            InputStreamReader isr = new InputStreamReader(fis);
            //用缓冲器读行
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            psStr = sb.toString();
            System.out.println(psStr);
            br.close();
            isr.close();
            fis.close();
            exec.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //过滤出用户未shell的那一行top进程信息
        ArrayList<String> topLines = null;
        if (psStr != null && !("".equals(psStr))) {
            String[] split = psStr.split("\\n");
            topLines = new ArrayList<String>();
            for (String s : split) {
                if (s.equals("")) {
                    continue;
                }
                topLines.add(s);
            }
        }
        if(topLines!=null){
            for (String topLine : topLines) {
                if (topLine.contains("0.0.0.0:8001")) {
                    proxyMessage = topLine;
                    break;
                }
            }
            System.out.println(proxyMessage);
        }

        //查找anyProxy进程的进程号
        ArrayList<String> anyProxyProcess = null;
        if (proxyMessage != null && !("".equals(proxyMessage))) {
            String[] split = proxyMessage.split(" ");
            anyProxyProcess = new ArrayList<String>();
            for (String s : split) {
                if(s.equals("")) continue;
                anyProxyProcess.add(s);
            }
        }

        if(anyProxyProcess!=null){
            if(anyProxyProcess.size()!=0) try {
                Runtime.getRuntime().exec("tskill " + anyProxyProcess.get(4));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
