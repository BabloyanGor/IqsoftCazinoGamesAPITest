//package utilities;//package utilities;
////
////import io.qameta.allure.Allure;
////import io.qameta.allure.Attachment;
////import org.apache.commons.io.FileUtils;
////import org.openqa.selenium.OutputType;
////import org.openqa.selenium.TakesScreenshot;
////import org.openqa.selenium.WebDriver;
////import org.testng.ITestContext;
////import org.testng.ITestListener;
////import org.testng.ITestResult;
////import org.testng.TestListenerAdapter;
////import pageObjects.BasePage;
////import testCases.WebCasinoGames_Tests.BaseTest;
////
////import java.io.ByteArrayInputStream;
////import java.io.File;
////import java.io.IOException;
////import java.util.Date;
////
////import static java.lang.String.format;
////
////public class ReportingAllure implements ITestListener {
////
////    private static String getTestMethodName(ITestResult iTestResult) {
////        return iTestResult.getMethod().getConstructorOrMethod().getName();
////    }


////    // Text attachments for Allure
////    @Attachment(value = "Page screenshot", type = "image/png")
////    public byte[] saveScreenshotPNG(WebDriver driver) {
////        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
////    }

////    // Text attachments for Allure
////    @Attachment(value = "{0}", type = "text/plain")
////    public static String saveTextLog(String message) {
////        return message;
////    }
////
////    // HTML attachments for Allure
////    @Attachment(value = "{0}", type = "text/html")
////    public static String attachHtml(String html) {
////        return html;
////    }
////
////    @Override
////    public void onStart(ITestContext iTestContext) {
////        saveTextLog((iTestContext.getName()) + " Test execution starts");
////    }
////
////    @Override
////    public void onFinish(ITestContext iTestContext) {
////        saveTextLog((iTestContext.getName()) + " Test Finished");
////    }
////
////    @Override
////    public void onTestStart(ITestResult iTestResult) {
////        saveTextLog(getTestMethodName(iTestResult) + " TestCase execution starts");
////    }
////
////    @Override
////    public void onTestSuccess(ITestResult iTestResult) {
////        saveTextLog(getTestMethodName(iTestResult) + " Test passed");
////    }
////
////    @Override
////    public void onTestFailure(ITestResult iTestResult) {
////        Object testClass = iTestResult.getInstance();
////        WebDriver driver = BaseTest.driver;
////        // Allure ScreenShotRobot and SaveTestLog
////
////        saveTextLog(getTestMethodName(iTestResult) + " Test failed and screenshot taken!");
////        if (driver instanceof WebDriver) {
////            Allure.addAttachment(getTestMethodName(iTestResult)+"_", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
////            saveScreenshotPNG(driver);
////        }
////        // Save a log on allure.
////
////    }
////
////    @Override
////    public void onTestSkipped(ITestResult iTestResult) {
////        WebDriver driver = BaseTest.driver;
////        // Allure ScreenShotRobot and SaveTestLog
////        saveTextLog(getTestMethodName(iTestResult) + " Test Skipped and screenshot taken!");
////        if (driver instanceof WebDriver) {
////            Allure.addAttachment(getTestMethodName(iTestResult)+"_", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
////            saveScreenshotPNG(driver);
////        }
////    }
////
////    @Override
////    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
////        // Allure ScreenShotRobot and SaveTestLog
////        WebDriver driver = BaseTest.driver;
////        saveTextLog(getTestMethodName(iTestResult) + " Test failed and screenshot taken!");
////        // Allure ScreenShotRobot and SaveTestLog
////        if (driver instanceof WebDriver) {
////            Allure.addAttachment(getTestMethodName(iTestResult)+"_", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
////            saveScreenshotPNG(driver);
////        }
////    }
////}
//
//
//import io.qameta.allure.Attachment;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//import pageObjects.BasePage;
//import testCases.WebCasinoGames_Tests.BaseTest;
//
//import java.awt.*;
//
//public class ReportingAllure extends BaseTest implements ITestListener {
//    private static String getTestMethodName(ITestResult iTestResult) {
//        return iTestResult.getMethod().getConstructorOrMethod().getName();
//    }
//
//    //Text attachments for Allure
//    @Attachment(value = "Page screenshot", type = "image/png")
//    public byte[] saveScreenshotPNG(WebDriver driver) {
//        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    }
//
//    //Text attachments for Allure
//    @Attachment(value = "{0}", type = "text/plain")
//    public static String saveTextLog(String message) {
//        return message;
//    }
//
//    //HTML attachments for Allure
//    @Attachment(value = "{0}", type = "text/html")
//    public static String attachHtml(String html) {
//        return html;
//    }
//
//    @Override
//    public void onStart(ITestContext iTestContext) {
////        logger.info("I am in onStart method " + iTestContext.getName());
//        iTestContext.setAttribute("WebDriver", this.driver);
//    }
//
//    @Override
//    public void onFinish(ITestContext iTestContext) {
////        logger.info("I am in onFinish method " + iTestContext.getName());
//        }
//
//    @Override
//    public void onTestStart(ITestResult iTestResult) {
////        logger.info(getTestMethodName(iTestResult) + " test is starting.");
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult iTestResult) {
////        logger.info(getTestMethodName(iTestResult) + " test is succeed.");
//    }
//
//    @Override
//    public void onTestFailure(ITestResult iTestResult) {
////        logger.info(getTestMethodName(iTestResult) + " test is failed.");
//
//        //Get driver from BaseTest and assign to local webdriver variable.
//        BasePage basePage = null;
//        try {
//            basePage = new BasePage();
//        } catch (AWTException e) {
//            throw new RuntimeException(e);
//        }
//        Object testClass = iTestResult.getInstance();
//        WebDriver driver = basePage.getDriver();
//
//        //Allure ScreenShotRobot and SaveTestLog
//        if (driver != null) {
//            System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
//            saveScreenshotPNG(driver);
//        }
//
//        //Save a log on allure.
//        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
//
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult iTestResult) {
////        logger.info(getTestMethodName(iTestResult) + " test is skipped.");
//    }
//
//    @Override
//    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
////        logger.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
//    }
//}