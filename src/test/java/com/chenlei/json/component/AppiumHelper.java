package com.chenlei.json.component;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AppiumHelper {


    /**
     * appium -p 4493 -bp 2252 -U "0af9a48503924f2c"
     * 初始化AndroidDriver，配置必要的参数。在setUp()中调用
     */
    public static AndroidDriver initAndroidDriver(AndroidDriver driver) throws MalformedURLException, HelperClassException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //不需要再次安装
        capabilities.setCapability("noReset", true);

        //no need sign 安装时不对apk进行重签名，设置很有必要，否则有的apk在重签名之后无法正常使用
        capabilities.setCapability("noSign", "True");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(CapabilityType.PLATFORM, "Android");
        capabilities.setCapability("deviceName", "0af9a48503924f2c");
        capabilities.setCapability("platformVersion", "6.0.1");
        capabilities.setCapability("appPackage", "com.rrh.jdb");
        capabilities.setCapability("appActivity", ".AppStartActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * Android4.4系统以上，支持最长三分钟的录制视频
     *
     * @throws IOException
     */
    public static void startRecord() throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd.exe /C adb shell screenrecord /sdcard/appiumRecord.mp4");
    }

    /**
     * 向左滑动一屏
     */
    public static void swipeToLeft(AndroidDriver driver) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width - 100, height / 2, 100, height / 2, 300);
    }

    /**
     * 向右滑动一屏
     */
    public static void swipeToRight(AndroidDriver driver) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(100, height / 2, width - 100, height / 2, 3);
    }

    /**
     * 向上滑动一屏
     */
    public static void swipeToUp(AndroidDriver driver) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width / 2, height - 400, width / 2, 400, 1000);
    }

    /**
     * 向下滑动一屏
     */
    public static void swipeToDown(AndroidDriver driver) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 300);
    }

    /**
     * 截图方法，以时间来命名，保存在工程的snapshot文件夹
     */
    public static void snapshot(AndroidDriver driver) throws HelperClassException, IOException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        String currentPath = System.getProperty("user.dir");
        String systemName = System.getProperty("os.name");
        String snapshotPath;
        if (systemName.equals("Mac OS X")) {
            snapshotPath = "//snapshot//picture//";
        } else {
            snapshotPath = "\\snapshot\\picture\\";
        }
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
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
        if (systemName.equals("Mac OS X")) {
            Reporter.log("<p><img width=\"256\" src=\"" + (jenkinsPicturePath + filename) + "\"/></p></a>");
        } else {
            Reporter.log("<p><img width=\"256\" src=\"" + file.getAbsoluteFile() + "\"/></p></a>");
        }


    }

    /**
     * banner向左滑动
     */
    public static void bannerSwipeToLeft(AndroidDriver driver) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        String xpath = "//android.support.v4.view.ViewPager[1]/android.widget.ImageView";
        WebElement byXPath = driver.findElementByXPath(xpath);
        int startx = byXPath.getSize().getWidth() - 50;
        int starty = byXPath.getLocation().getY() + byXPath.getSize().getHeight() / 2;
        int endx = byXPath.getLocation().getX() + 50;
        int endy = starty;
        driver.swipe(startx, starty, endx, endy, 300);
    }

    /**
     * 通过资源ID查询控件
     *
     * @param driver
     * @param resId
     * @return
     */
    public static WebElement findById(AndroidDriver driver, String resId) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(resId)) {
            throw new HelperClassException("resourceId can not be empty");
        }
        return driver.findElementById(resId);
    }

    /**
     * 通过资源ID来点击控件
     *
     * @param driver
     * @param resId
     */
    public static void clickById(AndroidDriver driver, String resId) throws HelperClassException {
        findById(driver, resId).click();
    }

    /**
     * 通过资源id来查询定位一组控件
     *
     * @param driver
     * @param resId
     * @return
     */
    public static List findElementsById(AndroidDriver driver, String resId) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(resId)) {
            throw new HelperClassException("resourceId can not be empty");
        }
        return driver.findElementsById(resId);
    }


    /**
     * 通过accessibilityId来查询控件
     *
     * @param driver
     * @param accessibilityId
     * @return
     */
    public static WebElement findByAccessibilityId(AndroidDriver driver, String accessibilityId) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(accessibilityId)) {
            throw new HelperClassException("accessibilityId can not be empty");
        }
        return driver.findElementByAccessibilityId(accessibilityId);
    }

    /**
     * 通过accessibilityId来点击控件
     *
     * @param driver
     * @param accessibilityId
     */
    public static void clickByAccessibilityId(AndroidDriver driver, String accessibilityId) throws HelperClassException {
        findByAccessibilityId(driver, accessibilityId).click();
    }

    /**
     * 通过类名来查询控件
     *
     * @param driver
     * @param className
     * @return
     */
    public static WebElement findByClassName(AndroidDriver driver, String className) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(className)) {
            throw new HelperClassException("className can not be empty");
        }
        return driver.findElementByClassName(className);
    }

    /**
     * 通过类名来点击控件
     *
     * @param driver
     * @param className
     */
    public static void clickByClassName(AndroidDriver driver, String className) throws HelperClassException {
        findByClassName(driver, className).click();
    }

    /**
     * 通过CssSelector来查询控件
     *
     * @param driver
     * @param CssSelector
     * @return
     */
    public static WebElement findByCssSelector(AndroidDriver driver, String CssSelector) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(CssSelector)) {
            throw new HelperClassException("CssSelector can not be empty");
        }
        return driver.findElementByCssSelector(CssSelector);
    }

    /**
     * 通过CssSelector来点击控件
     *
     * @param driver
     * @param CssSelector
     */
    public static void clickByCssSelector(AndroidDriver driver, String CssSelector) throws HelperClassException {
        findByCssSelector(driver, CssSelector).click();
    }

    /**
     * 通过LinkText来查询控件
     *
     * @param driver
     * @param linkText
     * @return
     */
    public static WebElement findByLinkText(AndroidDriver driver, String linkText) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(linkText)) {
            throw new HelperClassException("linkText can not be empty");
        }

        return driver.findElementByLinkText(linkText);
    }

    /**
     * 通过linkText来点击 控件
     *
     * @param driver
     * @param linkText
     */
    public void clickByLinkText(AndroidDriver driver, String linkText) throws HelperClassException {
        findByLinkText(driver, linkText).click();
    }

    /**
     * 通过名称来查询控件
     *
     * @param driver
     * @param name
     * @return
     */
    public static WebElement findByName(AndroidDriver driver, String name) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(name)) {
            throw new HelperClassException("name can not be empty");
        }
        return driver.findElementByName(name);
    }

    /**
     * 通过name来点击控件
     *
     * @param driver
     * @param name
     */
    public static void clickByName(AndroidDriver driver, String name) throws HelperClassException {
        findByName(driver, name).click();
    }

    /**
     * 通过PartialLinkText来查询控件
     *
     * @param driver
     * @param partialLinkText
     * @return
     */
    public static WebElement findByPartialLinkText(AndroidDriver driver, String partialLinkText) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(partialLinkText)) {
            throw new HelperClassException("partialLinkText can not be empty");
        }
        return driver.findElementByPartialLinkText(partialLinkText);
    }

    /**
     * 通过PartialLinkText来点击控件
     *
     * @param driver
     * @param partialLinkText
     */
    public static void clickByPartialLinkText(AndroidDriver driver, String partialLinkText) throws HelperClassException {
        findByPartialLinkText(driver, partialLinkText).click();
    }

    /**
     * 通过TagName来查询控件
     *
     * @param driver
     * @param tagName
     * @return
     */
    public static WebElement findByTagName(AndroidDriver driver, String tagName) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(tagName)) {
            throw new HelperClassException("tagName can not be empty");
        }
        return driver.findElementByTagName(tagName);
    }

    /**
     * 通过tagName来点击控件
     *
     * @param driver
     * @param tagName
     */
    public static void clickByTagName(AndroidDriver driver, String tagName) throws HelperClassException {
        findByTagName(driver, tagName).click();
    }

    /**
     * 通过XPath来查询控件
     *
     * @param driver
     * @param XPath
     * @return
     */
    public static WebElement findByXPath(AndroidDriver driver, String XPath) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(XPath)) {
            throw new HelperClassException("XPath can not be empty");
        }
        return driver.findElementByXPath(XPath);
    }

    /**
     * 通过XPath来点击控件
     *
     * @param driver
     * @param XPath
     */
    public static void clickByXPath(AndroidDriver driver, String XPath) throws HelperClassException {
        findByXPath(driver, XPath).click();
    }

    /**
     * 通过By的条件来查询控件
     *
     * @param driver
     * @param by
     * @return
     */
    public static WebElement findElement(AndroidDriver driver, By by) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if (by == null) {
            throw new HelperClassException("by can not be null");
        }
        return driver.findElement(by);
    }

    /**
     * 通过By的查询条件来点击控件
     *
     * @param driver
     * @param by
     */
    public static void clickElement(AndroidDriver driver, By by) throws HelperClassException {
        findElement(driver, by).click();
    }

}
