package com.chenlei.json.utils;

import com.chenlei.json.AbstractTestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenlei on 2017/9/19.
 */
public class ScreenShot {
    // 测试失败截屏保存的路径
    private String path;

    public ScreenShot(){
        path=System.getProperty("user.dir")+ "//snapshot//"+ this.getClass().getSimpleName()+"_"+getCurrentTime() + ".png";
    }

    public void getScreenShot() throws IOException {

        String currentPath = System.getProperty("user.dir");
        String systemName = System.getProperty("os.name");
        String snapshotPath;
        if(systemName.equals("Mac OS X")){
            snapshotPath = "//snapshot//picture//";
        }else {
            snapshotPath = "\\snapshot\\picture\\";
        }
        File scrFile = AbstractTestCase.driver.getScreenshotAs(OutputType.FILE);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmSS");
        String filename = formatter.format(new Date()) + ".png";
        FileUtils.copyFile(scrFile, new File(currentPath + snapshotPath + filename));

        String URL = currentPath + snapshotPath + filename;
        File file = new File(URL);
        System.setProperty("org.uncommons.reportng.escape-output", "false");

//        String absolute = file.getAbsolutePath();
//        int beginIndex = absolute.indexOf(".");
//        String relative = absolute.substring(beginIndex).replace(".\\", "");
//        String screenShot = relative.replace('\\', '/');
//        Reporter.log("\n" + "<a href=\"" + screenShot + "\"><p align=\"left\">screenshot at " + new Date() + "</p>" + "\n");
//        Reporter.log("<p><img width=\"256\" src=\"" + file.getAbsoluteFile() + "\" alt=\"screenshot at " + new Date() + "\"/></p></a><br/>");

        String jenkinsPicturePath = "http://client-jenkins.jdb-dev.com:8080/job/TestMockJson/ws/snapshot/picture/";
        if(systemName.equals("Mac OS X")){
            Reporter.log("<p><img width=\"256\" src=\"" + (jenkinsPicturePath+filename) + "\"/></p></a>");
//            Reporter.log(driver.getPageSource());
//            Reporter.log("<p><img width=\"256\" src=\"" + file.getAbsoluteFile() + "\"/></p></a>");
        }else {
            Reporter.log("<p><img width=\"256\" src=\"" + file.getAbsoluteFile() + "\"/></p></a>");
        }
    }


    /**
     * 获取当前时间
     */
    public String getCurrentTime(){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String currentTime=sdf.format(date);
        return currentTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
