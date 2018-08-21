package com.chenlei.json.utils;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.IOException;

/**
 * testNG执行case 失败后 ，testNG Listener会捕获执行失败
 * 如果要实现失败自动截图，需要重写Listener的onTestFailure方法
 *
 * @author chen
 */

public class TestNGListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult tr) {
        Reporter.log("Test Success");
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        Reporter.log("Test Failure");
        super.onTestFailure(tr);
        ScreenShot screenShot = new ScreenShot();
        try {
            screenShot.getScreenShot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTestSkipped(ITestResult tr) {
        Reporter.log("Test Skipped");
        super.onTestSkipped(tr);
    }

    @Override
    public void onStart(ITestContext testContext) {
        Reporter.log("Test Start");
        super.onStart(testContext);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        Reporter.log("Test Finish");
//        String caseCount = testContext.getCurrentXmlTest().getParameter("caseCount");
//        int caseCountNum = Integer.parseInt(caseCount);
//        System.out.println("case count : " + caseCount);
//
//        Iterator<ITestResult> listOfFailedTests = testContext.getFailedTests().getAllResults().iterator();
//
//        Iterator<ITestResult> listOfSkipedTests = testContext.getSkippedTests().getAllResults().iterator();
//        int passCaseNum = testContext.getPassedTests().getAllMethods().size();
//        if (passCaseNum >= caseCountNum) {
//            while (listOfFailedTests.hasNext()) {
//                ITestResult failedTest = listOfFailedTests.next();
//                ITestNGMethod method = failedTest.getMethod();
//                if (testContext.getFailedTests().getResults(method).size() > 1) {
//                    listOfFailedTests.remove();
//                } else {
//                    if (testContext.getPassedTests().getResults(method).size() > 0) {
//                        listOfFailedTests.remove();
//                    }
//                }
//            }
//            while (listOfSkipedTests.hasNext()) {
//                ITestResult skipedTest = listOfSkipedTests.next();
//                ITestNGMethod method = skipedTest.getMethod();
//                if (testContext.getSkippedTests().getResults(method).size() > 0) {
//                    listOfSkipedTests.remove();
//                }
//            }
//
//        }else {
//            while (listOfSkipedTests.hasNext()) {
//                ITestResult skipedTest = listOfSkipedTests.next();
//                ITestNGMethod method = skipedTest.getMethod();
//                if (testContext.getSkippedTests().getResults(method).size() > 0) {
//                    listOfSkipedTests.remove();
//                }
//            }
//        }


    }
}
