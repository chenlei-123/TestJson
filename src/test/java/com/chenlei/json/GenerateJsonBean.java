package com.chenlei.json;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


/**
 * Created by chenlei on 2017/5/8.
 */
public class GenerateJsonBean {
    public static void main(String[] args) throws IOException {
        String rootPath = System.getProperty("user.dir");
        System.out.println(rootPath);
        File file = new File(rootPath + "/ClassFile.txt");
        String jsonFileStr = FileUtils.readFileToString(file);
        String[] split = jsonFileStr.split("==================================");
        for (String javaTxt : split) {
            String[] strings = javaTxt.split("\n");
            for (String s : strings) {
                if(s.contains("public class")){
                    String[] name = s.split(" ");
                    String className = name[2];
                    System.out.println(className);
                    File javaFile = new File(rootPath+"/src/test/java/com/rrh/json/bean/"+className+".java");
                    FileUtils.writeStringToFile(javaFile,javaTxt,"UTF-8",true);
                    System.out.println(javaTxt);

                }
            }

        }
    }
}
