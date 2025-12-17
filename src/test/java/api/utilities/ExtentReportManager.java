package api.utilities;

import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.ImageHtmlEmail;
//import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter; // UI of the Report
	public ExtentReports extent; // populate common info on the Report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods
	
	String repName;
	
	public  void onStart(ITestContext context) {
		
	/*	SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt = new Date();
		String currentdatetimestamp = df.format(dt);*/
		
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-"+timestamp + ".html";
				
		
		System.out.println("Execution - On Start");
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
		
		sparkReporter.config().setDocumentTitle("PetStoreAPIAutomation Report"); // Title of the Report
		sparkReporter.config().setReportName("Pet Store User API Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "Pet Store Users API");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		
		
		List <String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
		
	}
	public void onTestStart(ITestResult result) {
	   System.out.println("Test Started");
	}
	
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Success");
		test = extent.createTest(result.getName());
				//.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display group in Report
		test.log(Status.PASS, "Test Case PASSED is:"+result.getTestClass().getName());
	}
	
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed");
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, "Test Case FAILED is:"+result.getName());
		test.log(Status.INFO, "Test Case FAILED is:"+ result.getThrowable().getMessage());
		
		/*
		 * try { BaseClass bc = new BaseClass(); String imgPath =
		 * bc.captureScreen(result.getTestClass().getName());
		 * test.addScreenCaptureFromPath(imgPath);
		 * 
		 * }catch(Exception e1) { e1.printStackTrace(); }
		 */	}
	
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Skipped");
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, "Test case SKIPPED is:"+result.getName());
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext context) {
		System.out.println("Execution Finshed");
		extent.flush();
		
/*		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		*/
/*		// Create Email Message 
		URL url;
		try {
			url = new URL("file://"+System.getProperty("user.dir")+"\\reports\\"+repName);
		
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("Jenifersheila26@gmail.com","AWSlearner26"));
			email.setSSLOnConnect(true);
			email.setFrom("Jenifersheila26@gmail.com");
			email.setSubject("Test Results");
			email.setMsg("Please find Attached Report");
			email.addTo("Jeniferanto@gmail.com");
			email.attach(url,"extentReport","please check report...");
			email.send();
		} catch (Exception e) {
	
			e.printStackTrace();
		}
*/		
	}

}
