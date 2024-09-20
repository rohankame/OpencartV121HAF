package utilities;

import TestBase.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.jetbrains.annotations.NotNull;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTRotY;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

//import javax.xml.crypto.Data;
import java.awt.Desktop;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;     // UI of the report
    public ExtentReports extent;     //populate common info on the report
    public ExtentTest test;   //create test case entries inthr report and update status of the test method
    String RepName;

    public ExtentReportManager() throws MalformedURLException {
    }


    public void onStart(ITestContext testContext) {
       /* SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt = new Date();
        String currentdatatimestamp = df.format(dt);*/
        //or
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        RepName = "Test-Report-" + timestamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + RepName);  //specify  the place where report will be generated

        sparkReporter.config().setDocumentTitle("Opencart Automation Report");   //title of the report
        sparkReporter.config().setReportName("Opencart Functional Testing");     //name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customer");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));  //it will give current user name of system
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includeGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includeGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());  //to display groups in report
        test.log(Status.PASS, result.getName() + "got successfully executed..");
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());  //to display groups in report
        test.log(Status.FAIL, result.getName() + "got Fail..");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());  //create a new entry in the reprt
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test case Skipped is:" + result.getName());    //update status p/f/s
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();

        String pathofExtentReport = System.getProperty("user.dir") + "\\reports\\" + RepName;
        File extentReport = new File(pathofExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //send report through email aumatically just after the generating report
       /* try {
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + RepName);

            //creating the email message
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("rohankame2018@gmail.com", "password"));
            email.setSSLOnConnect(true);
            email.setFrom("rohankame2018@gmail.com"); //sender
            email.setSubject("Test Result");
            email.setMsg("please find attached report..");
            email.addTo("rohankame.busyqa@gmail.com");  //receiver
            email.attach(url, "extent report", "please check report...");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}

