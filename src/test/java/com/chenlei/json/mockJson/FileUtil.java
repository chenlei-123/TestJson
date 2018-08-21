package com.chenlei.json.mockJson;

import com.chenlei.json.JsonFormatTool;

import java.io.*;
import java.util.concurrent.ExecutorService;

/**
 * 线程池 写文件
 * Created by linliping on 2017/7/27.
 */
 class FileUtil {

    private ExecutorService mFixedThreadPool = null;
    private static FileUtil mInstance = null;

    public static FileUtil getInstance() {
        if(mInstance == null) {
            mInstance = new FileUtil();
        }
        return mInstance;
    }

    //对外暴露的写文件方法
    public void writeTxt2File(final String absoluteFilePath, final String fileName, final String jsonText){
//        线程池，开启5个线程
//        if(mFixedThreadPool == null) {
//            mFixedThreadPool = Executors.newFixedThreadPool(5);
//        }
//        mFixedThreadPool.execute(new Runnable() {
//            public void run() {
//                File file = new File(absoluteFilePath, fileName);
//                try {
//                    if(createFile(file)) {
//                        writeTxtFile(jsonText, file);
//                        System.out.println("写文件:" + "file path: " + absoluteFilePath + "; fileName: " + fileName + " is over and ok~");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        new Thread(new Runnable() {
            public void run() {
                File file = new File(absoluteFilePath, fileName);
                try {
                    if(createFile(file)) {
                        writeTxtFile(jsonText, file);
                        System.out.println("写文件:" + "file path: " + absoluteFilePath + "; fileName: " + fileName + " is over and ok~");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 创建文件
     *
     * @param fileName
     * @return
     */
    private boolean createFile(File fileName) throws Exception {
        boolean flag = false;
        try {
            if (fileName.exists()) {
                fileName.delete();
            }
            flag = fileName.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 创建文件夹
     *
     * @param folderPath    返回文件夹的路径
     */
    public String mkdirFile(String folderPath) {
        folderPath = folderPath.replace(".", "_");
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    //写文件至txt文件
    private boolean writeTxtFile(String content, File fileName) throws Exception {
        JsonFormatTool formatTool = new JsonFormatTool();
        String formatJson = formatTool.formatJson(content);

        RandomAccessFile mm = null;
        boolean flag = false;
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(fileName);
            o.write(formatJson.getBytes("UTF-8"));
            o.close();
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (mm != null) {
                mm.close();
            }
        }
        return flag;
    }

    /**
     * 读TXT文件内容
     *
     * @param file
     * @return
     */
    public String readTxtFile(File file) throws Exception {
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                result.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        System.out.println("读出的文件内容为：" + result.toString());
        return result.toString();
    }
}
