package com.chenlei.json.testcase;

import com.chenlei.json.AbstractTestCase;
import com.chenlei.json.component.HelperClassException;
import com.chenlei.json.component.UIAutomatorHelper;
import com.chenlei.json.component.AppiumHelper;
import com.chenlei.json.component.TestHelper;
import com.chenlei.json.utils.JDBLog;
import com.chenlei.json.utils.ProxyServerForMac;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenlei on 2017/11/29.
 */
public class TestGetBorrowInfo extends AbstractTestCase {
    private final String TAG = this.getClass().getSimpleName();
    public static ArrayList<File> filelist = new ArrayList<File>();

    @DataProvider(name = "data")
    public Object[][] data(){

        String projectPath = System.getProperty("user.dir");
        ArrayList<String> jsonNames = new ArrayList<String>();
        List<File> fileList = getFileList(projectPath + "/tmp/data/");
        for (File file : fileList) {
            jsonNames.add(file.toString());
        }
        Object[][] object = new Object[jsonNames.size()][1];
        for (int i = 0; i <jsonNames.size(); i++) {
            object[i][0] = jsonNames.get(i);
        }
        return object;
    }

    @BeforeMethod
    public void setUp() {
        driver.closeApp();
        TestHelper.killProxyServer();
    }

    @Test(dataProvider = "data")
    public void data(String data) throws InterruptedException, HelperClassException {

        System.out.println(data);
        String[] split = data.split("tmp");
        String fileName = split[1];

        String projectPath = System.getProperty("user.dir");
        String path = projectPath+"/tmp/";
        JDBLog.info(TAG,path);
        JDBLog.info(TAG,fileName);
        new ProxyServerForMac(path,fileName).start();
        Thread.sleep(3000);
        driver.launchApp();
        waitFirstPageLoadFinish(driver,TAG);
        JDBLog.info(TAG,"点击我要借钱");
        UIAutomatorHelper.clickByUIAResId(driver,"com.rrh.jdb:id/ivBorrow");
        Thread.sleep(4000);
        try {
            WebElement byUIAText = UIAutomatorHelper.findByUIAText(driver, "数据格式出错");
            if(byUIAText.isDisplayed()){
                Assert.assertTrue(true);
            }
        }catch (NoSuchElementException e){
        }
        waitViewDisplay("借钱");
    }


    @AfterMethod
    public void tearDown() throws Exception {
        AppiumHelper.snapshot(driver);
        driver.closeApp();
        TestHelper.killProxyServer();
    }

    private void waitViewDisplay(String text){
        try {
            final WebDriverWait wait = new WebDriverWait(driver, 30);
            Assert.assertNotNull(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'"+text+"')]"))));
            System.out.println("找到了toast");
        } catch (Exception e) {
            throw new AssertionError("找不到" );
        }
    }

    private void waitFirstPageLoadFinish(AndroidDriver driver, String TAG) throws HelperClassException {
        long expectTime = System.currentTimeMillis() + 30 * 1000;
        do {
            try {
                AppiumHelper.findById(driver, "com.rrh.jdb:id/loading_view").isDisplayed();
                JDBLog.info(TAG,"首页 loading！！");
            } catch (NoSuchElementException e) {
                break;
            } catch (NullPointerException e) {
                break;
            }
        } while (System.currentTimeMillis()<expectTime);
    }

    public List<File> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("json")) { // 判断文件名是否以.json
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }
        }
        return filelist;
    }


}
