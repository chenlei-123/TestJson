package com.chenlei.json.component;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class UIAutomatorHelper {
    /**
     * 通过UiAutomator的UiSelector来定位控件
     *
     * @param driver
     * @param UiSelector
     * @return
     */
    public static WebElement findByUIA(AndroidDriver driver, String UiSelector) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(UiSelector)) {
            throw new HelperClassException("UiSelector can not be empty");
        }
        return driver.findElementByAndroidUIAutomator(UiSelector);
    }

    /**
     * @param driver
     * @param UiSelector
     * @return
     */
    public static List findElementsByUIA(AndroidDriver driver, String UiSelector) {
        return driver.findElementsByAndroidUIAutomator(UiSelector);
    }

    /**
     * 通过UiAutomator的UiSelector来点击控件
     *
     * @param driver
     * @param UiSelector
     */
    public static void clickByUIA(AndroidDriver driver, String UiSelector) throws HelperClassException {
        findByUIA(driver, UiSelector).click();
    }

    /**
     * 通过UIAutomator的Text属性来查询控件
     *
     * @param driver
     * @param UIAutomatorText
     * @return
     */
    public static WebElement findByUIAText(AndroidDriver driver, String UIAutomatorText) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(UIAutomatorText)) {
            throw new HelperClassException("UIAutomatorText can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + UIAutomatorText + "\")");
    }

    /**
     * 通过UIAutomator的Text属性来点击控件
     *
     * @param driver
     * @param UIAutomatorText
     */
    public static void clickByUIAText(AndroidDriver driver, String UIAutomatorText) throws HelperClassException {
        findByUIAText(driver, UIAutomatorText).click();
    }

    /**
     * 通过UIAutomator的Text属性Contains包含查询控件
     *
     * @param driver
     * @param TextContains
     * @return
     */
    public static WebElement findByUIATextContains(AndroidDriver driver, String TextContains) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(TextContains)) {
            throw new HelperClassException("TextContains can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + TextContains + "\")");
    }

    /**
     * 通过UIAutomator的Text属性Contains包含点击控件
     *
     * @param driver
     * @param TextContains
     */
    public static void clickByUIATextContains(AndroidDriver driver, String TextContains) throws HelperClassException {
        findByUIATextContains(driver, TextContains).click();
    }

    /**
     * 通过UIAutomator的Text正则属性来查询控件
     *
     * @param driver
     * @param textRegex
     * @return
     */
    public static WebElement findByUIATextMatches(AndroidDriver driver, String textRegex) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(textRegex)) {
            throw new HelperClassException("textRegex can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().textMatches(\"" + textRegex + "\")");
    }

    /**
     * 通过UIAutomator的Text正则属性来点击控件
     *
     * @param driver
     * @param textRegex
     */
    public static void clickByUIATextMatches(AndroidDriver driver, String textRegex) throws HelperClassException {
        findByUIATextMatches(driver, textRegex).click();
    }

    /**
     * 通过UIAutomator的TextStartWith查询控件
     *
     * @param driver
     * @param textStartWith
     * @return
     */
    public static WebElement findByUIATextStartsWith(AndroidDriver driver, String textStartWith) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(textStartWith)) {
            throw new HelperClassException("textStartWith can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().textStartsWith(\"" + textStartWith + "\")");
    }

    /**
     * 通过UIAutomator的TextStartWith点击控件
     *
     * @param driver
     * @param textStartWith
     */
    public static void clickByUIATextStartsWith(AndroidDriver driver, String textStartWith) throws HelperClassException {
        findByUIATextStartsWith(driver, textStartWith).click();
    }

    /**
     * 根据UiAutomator的resid来查询控件
     *
     * @param driver
     * @param UIAutomatorResId
     * @return
     */
    public static WebElement findByUIAResId(AndroidDriver driver, String UIAutomatorResId) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(UIAutomatorResId)) {
            throw new HelperClassException("UIAutomatorResId can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"" + UIAutomatorResId + "\")");
    }

    /**
     * 根据UiAutomator的resid来点击控件
     *
     * @param driver
     * @param UIAutomatorResId
     * @return
     */
    public static void clickByUIAResId(AndroidDriver driver, String UIAutomatorResId) throws HelperClassException {
        findByUIAResId(driver, UIAutomatorResId).click();
    }

    /**
     * 根据UiAutomator的resid正则来查询控件
     *
     * @param driver
     * @param ResIdMatch
     * @return
     */
    public static WebElement findByUIAResIdMatch(AndroidDriver driver, String ResIdMatch) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(ResIdMatch)) {
            throw new HelperClassException("ResIdMatch can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().resourceIdMatches(\"" + ResIdMatch + "\")");
    }

    /**
     * 根据UiAutomator的resid正则来点击控件控件
     *
     * @param driver
     * @param ResIdMatch
     */
    public static void clickByUIAResIdMatch(AndroidDriver driver, String ResIdMatch) throws HelperClassException {
        findByUIAResIdMatch(driver, ResIdMatch).click();
    }

    /**
     * 根据UiAutomator的属性description来查询控件
     *
     * @param driver
     * @param des
     * @return
     */
    public static WebElement findByUIADes(AndroidDriver driver, String des) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(des)) {
            throw new HelperClassException("des can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + des + "\")");
    }

    /**
     * 根据UiAutomator的属性description来点击控件
     *
     * @param driver
     * @param des
     * @return
     */
    public static void clickByUIADes(AndroidDriver driver, String des) throws HelperClassException {
        findByUIADes(driver, des).click();
    }

    /**
     * 根据UiAutomator的属性desContains来查询控件
     *
     * @param driver
     * @param desContains
     * @return
     */
    public static WebElement findByUIADesContains(AndroidDriver driver, String desContains) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(desContains)) {
            throw new HelperClassException("desContains can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().descriptionContains(\"" + desContains + "\")");
    }

    /**
     * 根据UiAutomator的属性desContains来点击控件
     *
     * @param driver
     * @param desContains
     * @return
     */
    public static void clickByUIADesContains(AndroidDriver driver, String desContains) throws HelperClassException {
        findByUIADesContains(driver, desContains).click();
    }

    /**
     * 根据UiAutomator的属性desMatches来查询控件
     *
     * @param driver
     * @param desMatches
     * @return
     */
    public static WebElement findByUIADesMatches(AndroidDriver driver, String desMatches) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(desMatches)) {
            throw new HelperClassException("desMatches can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().descriptionMatches(\"" + desMatches + "\")");
    }

    /**
     * 根据UiAutomator的属性desMatches来点击控件
     *
     * @param driver
     * @param desMatches
     * @return
     */
    public static void clickByUIADesMatches(AndroidDriver driver, String desMatches) throws HelperClassException {
        findByUIADesMatches(driver, desMatches).click();
    }

    /**
     * 根据UiAutomator的属性desStartsWith来查询控件
     *
     * @param driver
     * @param desStartsWith
     * @return
     */
    public static WebElement findByUIADesStartsWith(AndroidDriver driver, String desStartsWith) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(desStartsWith)) {
            throw new HelperClassException("desStartsWith can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().descriptionStartsWith(\"" + desStartsWith + "\")");
    }

    /**
     * 根据UiAutomator的属性desStartsWith来点击控件
     *
     * @param driver
     * @param desStartsWith
     * @return
     */
    public static void clickByUIADesStartsWith(AndroidDriver driver, String desStartsWith) throws HelperClassException {
        findByUIADesStartsWith(driver, desStartsWith).click();
    }

    /**
     * 根据UiAutomator的属性ClassName查询击控件
     *
     * @param driver
     * @param className
     * @return
     */
    public static WebElement findByUIAClassName(AndroidDriver driver, String className) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(className)) {
            throw new HelperClassException("className can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().className(\"" + className + "\")");
    }

    /**
     * 根据UiAutomator的属性ClassName点击控件
     *
     * @param driver
     * @param className
     * @return
     */
    public static void clickByUIAClassName(AndroidDriver driver, String className) throws HelperClassException {
        findByUIAClassName(driver, className).click();
    }

    /**
     * 根据UiAutomator的属性classNameMatches查询控件
     *
     * @param driver
     * @param classNameMatches
     * @return
     */
    public static WebElement findByUIAClassNameMatches(AndroidDriver driver, String classNameMatches) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(classNameMatches)) {
            throw new HelperClassException("classNameMatches can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().classNameMatches(\"" + classNameMatches + "\")");
    }

    /**
     * 根据UiAutomator的属性classNameMatches点击控件
     *
     * @param driver
     * @param classNameMatches
     * @return
     */
    public static void clickByUIAclassNameMatches(AndroidDriver driver, String classNameMatches) throws HelperClassException {
        findByUIAClassNameMatches(driver, classNameMatches).click();
    }

    /**
     * 根据UiAutomator的属性index查询控件
     *
     * @param driver
     * @param index
     * @return
     */
    public static WebElement findByUIAIndex(AndroidDriver driver, int index) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }

        return driver.findElementByAndroidUIAutomator("new UiSelector().index(" + index + ")");
    }

    /**
     * 根据UiAutomator的属性index点击控件
     *
     * @param driver
     * @param index
     * @return
     */
    public static void clickByUIAIndex(AndroidDriver driver, int index) throws HelperClassException {
        findByUIAIndex(driver, index).click();
    }

    /**
     * 根据UiAutomator的属性pkgName查询控件
     *
     * @param driver
     * @param pkgName
     * @return
     */
    public static WebElement findByUIAPackageName(AndroidDriver driver, String pkgName) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(pkgName)) {
            throw new HelperClassException("pkgName can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().packageName(\"" + pkgName + "\")");
    }

    /**
     * 根据UiAutomator的属性pkgName点击控件
     *
     * @param driver
     * @param pkgName
     * @return
     */
    public static void clickByUIAPackageName(AndroidDriver driver, String pkgName) throws HelperClassException {
        findByUIAPackageName(driver, pkgName).click();
    }

    /**
     * 根据UiAutomator的当前控件的兄弟类中查询控件
     *
     * @param driver
     * @param
     * @return
     */
    public static WebElement findByUIAFromParent(AndroidDriver driver, String UiSelector) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(UiSelector)) {
            throw new HelperClassException("UiSelector can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().fromParent(" + UiSelector + ")");
    }

    /**
     * 根据UiAutomator的当前控件的兄弟类中查询控件点击
     *
     * @param driver
     * @param
     * @return
     */
    public static void clickByUIAFromParent(AndroidDriver driver, String UiSelector) throws HelperClassException {
        findByUIAFromParent(driver, UiSelector).click();
    }

    public static WebElement findByUIAChildSelector(AndroidDriver driver, String UiSelector) throws HelperClassException {
        if (driver == null) {
            throw new HelperClassException("driver can not be null");
        }
        if ("".equals(UiSelector)) {
            throw new HelperClassException("UiSelector can not be empty");
        }
        return driver.findElementByAndroidUIAutomator("new UiSelector().childSelector(" + UiSelector + ")");
    }

    public static void clickByUIAChildSelector(AndroidDriver driver, String UiSelector) throws HelperClassException {
        findByUIAChildSelector(driver, UiSelector).click();
    }


}
