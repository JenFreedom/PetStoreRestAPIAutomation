package api.utilities;

import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
	private ExtentSparkReporter sparkReporter; // UI of the Report
	private ExtentReports extent; // populate common info on the Report
	
	//ThreadLocal ensures each Thread has its own ExtenText - safe for parallel runs
	private ThreadLocal<ExtentTest> test = new ThreadLocal<>(); // creating test case entries in the report and update status of the test methods
	
	private String repName;
	
	@Override
	public  void onStart(ITestContext context) {
		
	/*	SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt = new Date();
		String currentdatetimestamp = df.format(dt);*/
		
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-"+timestamp + ".html";
				
		String reportPath = System.getProperty("user.dir") + File.separator + "reports"
							+ File.separator+repName ;
		sparkReporter = new ExtentSparkReporter(reportPath);
		System.out.println("Execution - On Start");
		
		sparkReporter.config().setDocumentTitle("PetStore API Automation Report"); // Title of the Report
		sparkReporter.config().setReportName("Pet Store User API Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		//System info
		extent.setSystemInfo("Application", "Pet Store Users API");
		extent.setSystemInfo("Environment", System.getProperty("env","dev"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		
		
		List <String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
		logger.info("Extent Report started: "+reportPath);
	}
	@Override
	public void onTestStart(ITestResult result) {
		// Create test entry here
		ExtentTest extentTest = extent.createTest(result.getName());
		extentTest.assignCategory(result.getMethod().getGroups());
		test.set(extentTest);
		logger.info("Test Started " + result.getName());
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Success");
		test.get().log(Status.PASS, "Test Case PASSED: "+result.getName());
		logger.info("TestPassed: "+result.getName());
	}
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed");
//		test = extent.createTest(result.getName());
//		test.assignCategory(result.getMethod().getGroups());
		
		test.get().log(Status.FAIL, "Test Case FAILED is:"+result.getName());
		test.get().log(Status.INFO, "Failure Reason: "+ result.getThrowable().getMessage());
		logger.error("Test Failed: " +result.getName() + "| Reason: "+result.getThrowable().getMessage());
		
		/*
		 * try { BaseClass bc = new BaseClass(); String imgPath =
		 * bc.captureScreen(result.getTestClass().getName());
		 * test.addScreenCaptureFromPath(imgPath);
		 * 
		 * }catch(Exception e1) { e1.printStackTrace(); }
		 */	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Skipped");
		test.get().log(Status.SKIP, "Test case SKIPPED is:"+result.getName());
		if (result.getThrowable()!=null) {
			test.get().log(Status.INFO, "Skip Reason: "+result.getThrowable().getMessage());
		}
		logger.warn("test Skipped: " + result.getName());
	}
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Execution Finshed");
		extent.flush();
		logger.info("Extent Report save:  "+ System.getProperty("user.dir")
							+File.separator + "reports" + File.separator + repName);
		
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
