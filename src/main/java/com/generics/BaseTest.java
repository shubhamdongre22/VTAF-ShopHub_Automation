package com.generics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlTest;

public class BaseTest extends Pojo {
	private Properties objConfig;
	private String callingClassName = "";
	private InitializeTearDownEnvironment objInitializeTearDownEnvironment;
	private FileInputStream excelFileIS;




	public void initializeWebEnvironment(String url)   {

		this.callingClassName = this.getClass().getName();
		this.setRunningScriptName(this.callingClassName);
		this.objInitializeTearDownEnvironment = new InitializeTearDownEnvironment();
		// objInitializeTearDownEnvironment.killBrowserAndDriver(objConfig);
		this.setDriver(this.objInitializeTearDownEnvironment.initializeWebEnvironment(this.objConfig));
		this.setAfterClickwait(Integer.parseInt(this.objConfig.getProperty("AfterClickWait")));
		this.setScriptTimeoutWait(Integer.parseInt(this.objConfig.getProperty("ScriptTimeoutWait")));
		this.setWebDriverWait(new WebDriverWait(this.getDriver(),
				Integer.parseInt(this.objConfig.getProperty("driver.WebDriverWait").trim())));
		this.setObjUtilities(new Utilities(this));
		this.setObjWrapperFunctions(new WrapperFunctions(this));
		this.loadDataProvider();
		this.loadBaseURL(url);
		this.createLo4jLogerDirectory();
	}

	public void loadBaseURL(String baseurl) {
		//String RunningMethod = Thread.currentThread().getStackTrace()[4].getMethodName();
		//String baseurl = this.objConfig.getProperty("web.protocol") + "://" + this.objConfig.getProperty("web.domain")
				//+ this.objConfig.getProperty("web.path");
		this.getDriver().get(baseurl);
		this.getObjUtilities().logReporter("Open URL - '" + baseurl + "'.", true);

	}

	public void tearDownWebEnvironment() {
		try {
			this.getDriver().quit();
			setObjUtilities(null);
			this.getObjWrapperFunctions().waitFor(2);
//			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			System.out.println("Kill Chrome Browser.!!!!!!");
		} catch (Exception var4) {
			try {
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			} catch (IOException var3) {
				var3.printStackTrace();
			}
			var4.printStackTrace();
			if (System.getProperty("web.browser").trim().equalsIgnoreCase("IE")
					|| System.getProperty("web.browser").trim().equalsIgnoreCase("Chrome")) {
				this.killBrowserAndDriver(this.objConfig);
			}
		}
		this.getObjWrapperFunctions().waitFor(4);
		this.setObjWrapperFunctions(null);
	}

	protected void killBrowserAndDriver(Properties objConfig) {
		String browser = System.getProperty("web.browser").trim();
		String browserProcess = "";
		String driverProcess = "";
		if (!browser.equals("") && browser.equalsIgnoreCase("IE")) {
			browserProcess = "iexplore";
			driverProcess = "IEDriverServer.exe";
		} else if (!browser.equals("") && browser.equalsIgnoreCase("Chrome")) {
			browserProcess = browser;
			driverProcess = "chromedriver.exe";
		}

		try {
			Process procDriver = Runtime.getRuntime().exec("taskkill /F /T /IM " + driverProcess);
			Process procIE = Runtime.getRuntime().exec("taskkill /F /T /IM " + browserProcess + ".exe");
			procDriver.waitFor();
			procIE.waitFor();
		} catch (Exception var7) {
			var7.printStackTrace();
		}

	}

	public void createLo4jLogerDirectory() {
		String logFilePath = reportPath + "/custom-reports/log4j-Logger.log";
		File reportFile = new File(logFilePath);
		try {
			if (!reportFile.exists()) {
				(new File(reportPath + "/custom-reports")).mkdir();
				reportFile.createNewFile();
			}
		} catch (IOException var4) {
			var4.printStackTrace();
		}
	}

	public boolean cleanDownloadsDirectory() {
		try {
			String downloadFilesLocation = System.getProperty("user.dir")
					+ this.objConfig.getProperty("downloads.path").trim();
			File directory = new File(downloadFilesLocation);
			FileUtils.cleanDirectory(directory);
			return true;
		} catch (Exception var3) {
			var3.printStackTrace();
			return false;
		}
	}

	synchronized public void loadDataProvider() {
		try {
			String testDataPath = objConfig.getProperty("testdata.path");
			if (!testDataPath.contains(":")) {
				testDataPath = System.getProperty("user.dir") + testDataPath;
			}
			testDataPath = testDataPath + objConfig.getProperty("testdata.fileName") + ".xlsx";
			excelFileIS = new FileInputStream(testDataPath);
			setWorkbook(new XSSFWorkbook(excelFileIS));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@BeforeTest
	public void setupExecutionEnviornment(ITestContext ctx, XmlTest test) {

	    try {

	        this.objConfig = new Properties();

	        this.objConfig.load(
	            new FileInputStream(
	                System.getProperty("user.dir")
	                + "/src/test/resources/config.properties"
	            )
	        );

	        this.setObjConfig(this.objConfig);

	        // ADD THIS
	        //reportPath = System.getProperty("user.dir")
	               // + this.objConfig.getProperty("report.path");

	    } catch (Exception var2) {
	        var2.printStackTrace();
	    }
	}

	    // baaki existing code...



	public String getProperty(String propertyName) {
	    return this.objConfig.getProperty(propertyName);
	}

//	@BeforeMethod
//	public void startReportings(Method method) {
//		this.setExtentTest(this.getExtent().createTest(method.getName()));
//	}
//
//	@AfterSuite
//	public void flushReportings() {
//		this.getExtent().flush();
//	}
}