package com.chenlei.json.utils;

import org.testng.*;
import org.testng.collections.Lists;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class TestNGSimpleReport implements ITestListener, IReporter {
    private List<String> testPassed = Lists.newArrayList();
    private List<String> testFailed = Lists.newArrayList();
    private List<String> testSkipped = Lists.newArrayList();

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        System.out.println("Passed Case: " + testPassed.size());
        System.out.println("testFailed Case: " + testFailed.size());
        System.out.println("testSkipped Case: " + testSkipped.size());

        for (String passed : testPassed) {
            System.out.println("passed case:" + passed);
        }
        for (String passed : testFailed) {
            System.out.println("failed case:" + passed);
        }

        for (String passed : testSkipped) {
            System.out.println("skipped case:" + passed);
        }

    }

    public void onTestStart(ITestResult result) {

    }

    public void onTestSuccess(ITestResult result) {
        testPassed.add(result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        testFailed.add(result.getMethod().getMethodName());
    }

    public void onTestSkipped(ITestResult result) {
        testSkipped.add(result.getMethod().getMethodName());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {

    }
}
