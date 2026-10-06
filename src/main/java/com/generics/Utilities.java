package com.generics;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;

/**
 * @ScriptName : Utilities
 * @Description : This class contains utilities function
 * @Author : Uday Naik - Valethi Technologies
 */
public class Utilities {
	private Pojo objPojo;

	// ExtentReports reports;
	// ExtentTest test;

	public Utilities(Pojo pojo) {
		this.objPojo = pojo;
	}

	/**
	 * @Method : logReporter
	 * @Description : Reporter method
	 * @param : Step - Step description, resultLog - result log pass/fail
	 *          (true/false), includeMobile - result for mobile(true/false)
	 * @Author : Uday Naik - Valethi Technologies
	 */
	@Step("{0}")
	public void logReporter(String step, boolean resultLog) {
		String strLog = step;
		this.addAssertTakeScreenShot(step, strLog, "", "", "", resultLog);
	}

	/**
	 * @Method : logReporter
	 * @Description : Reporter method
	 * @param : Step - Step description, inputValue - Input value, resultLog -
	 *          result log pass/fail (true/false), includeMobile - result for
	 *          mobile(true/false)
	 * @Author : Uday Naik - Valethi Technologies
	 */
	@Step("{0} - {1}")
	// @Step("{0}")
	public void logReporter(String step, String inputValue, boolean resultLog) {
		String strLog = step + " || Input Value : " + inputValue;
		this.addAssertTakeScreenShot(step, strLog, inputValue, "", "", resultLog);
	}

	/**
	 * @Method : logReporter
	 * @Description : Reporter method
	 * @param : Step - Step description, expectedValue - verification point expected
	 *          value, actualValue - verification point actual value, resultLog -
	 *          result log pass/fail (true/false), includeMobile - result for
	 *          mobile(true/false)
	 * @Author : Uday Naik - Valethi Technologies
	 */
	@Step("{0} - {1} - {2}")
	// @Step("{0}")
	public void logReporter(String step, String expectedValue, String actualValue, boolean resultLog) {
		String strLog = step + " || Expected Result : " + expectedValue + " || Actual Result : " + actualValue;
		this.addAssertTakeScreenShot(step, strLog, "", expectedValue, actualValue, resultLog);
	}

	/**
	 * @Method : addAssertTakeScreenShot
	 * @Description :
	 * @param :
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public void addAssertTakeScreenShot(String step, String strLog, String inputValue, String expectedValue,
			String actualValue, boolean resultLog) {
		System.out.println(objPojo.getTestCaseID() + "--> " + strLog);
		Logger logger = Logger.getLogger(Utilities.class);
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("src\\test\\resources\\log4j.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String loggerpath = Pojo.reportPath + "/custom-reports/log4j-Logger.log";
		props.setProperty("log4j.appender.file.File", loggerpath);
		PropertyConfigurator.configure(props);

		if (resultLog) {
			Reporter.log("Step Description--> " + strLog);
			logger.info("Step Description--> " + objPojo.getTestCaseID() + "---" + strLog);
			Allure.step(strLog, Status.PASSED);
			Assert.assertTrue(true);
			// reports=new
			// ExtentReports("D:\\TestAutomation\\Report\\ExtentReports\\report.html");
			// test=reports.startTest("Extent Report");
			// test.log(LogStatus.PASS, strLog);

		} else {
			String fileName = getDateInSpecifiedFormat("dd_MMM_yyyy_HH_mm_ss") + "_TCID_" + objPojo.getTestCaseID()
					+ ".png";
			String fileWithPath = Pojo.reportPath + "\\surefire-reports\\ScreenShot\\" + fileName;
			Reporter.log("Step Description--> " + strLog);
			logger.error("Step Description--> " + objPojo.getTestCaseID() + "---" + strLog);
			System.out.println("URl :-------------> " + objPojo.getDriver().getCurrentUrl());
			logger.error(
					"URl :-------------> " + objPojo.getTestCaseID() + "---" + objPojo.getDriver().getCurrentUrl());
			this.takeScreenShot(objPojo.getDriver(), fileWithPath, "Step Description--> " + strLog);
			Reporter.log("<html><body><h2>ScreenShot On Fail Page</h2><img src=" + fileWithPath + "></body></html>");
			Allure.step(strLog, Status.FAILED);
			Assert.assertTrue(false);

		}
	}

	/**
	 * @Method : takeScreenShot
	 * @Description : Take Screen shot for given web driver.
	 * @Author : Uday Naik - Valethi Technologies
	 *
	 */
	public boolean takeScreenShot(WebDriver webDriver, String fileWithPath, String strLog) {
		TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
		Allure.addAttachment("Any text", new ByteArrayInputStream(scrShot.getScreenshotAs(OutputType.BYTES)));
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(fileWithPath);
		try {
			FileUtils.moveFile(srcFile, destFile);
			this.fileToByte(destFile);
			return true;
		} catch (IOException iOException) {
			iOException.printStackTrace();
			return false;

		}
	}

	/**
	 * @Method : getDateInSpecifiedFormat
	 *
	 * @Description : This method takes parameter of your required DateFormat Type
	 *              Like: dd-mm-YYYY DD.MM.YYYY and in return it will give you
	 *              today's date in specified date format
	 *
	 * @param : dateFormat like : dd-MM-YYYY
	 *
	 * @Author : Uday Naik - Valethi Technologies
	 *
	 **/
	public String getDateInSpecifiedFormat(String dateFormat) {
		String current_date = "";
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		current_date = formatter.format(today);
		return current_date;
	}

	/**
	 * @Method : fileToByte
	 * @Description : Converts image file to byte array for allure.
	 * @Author : Uday Naik - Valethi Technologies
	 * @throws : IOException
	 */
	@Attachment(value = "Screenshot", type = "image/png")
	private byte[] fileToByte(File file) throws IOException {
		if (file != null) {
			return Files.readAllBytes(Paths.get(file.getPath()));
		} else {
			return new byte[0];
		}
	}

	/**
	 * Generate random string
	 *
	 * @return String random string value
	 */
	public String getRandomString(int lenght) {
		String allowedChars = "abcdefghiklmnopqrstuvwxyz";
		String randomstring = "";
		for (int i = 0; i < lenght; i++) {
			int rnum = (int) Math.floor(Math.random() * allowedChars.length());
			randomstring += allowedChars.substring(rnum, rnum + 1);
		}
		return randomstring.toUpperCase();
	}

	/**
	 * Generate random string with numbers
	 *
	 * @return String random string value
	 */
	public String getRandomNumbers(int length) {
		String allowedChars = "1234567890";
		String randomstring = "";
		for (int i = 0; i < length; i++) {
			int rnum = (int) Math.floor(Math.random() * allowedChars.length());
			randomstring += allowedChars.substring(rnum, rnum + 1);
		}
		return randomstring;
	}

	/**
	 * @author Uday Naik - Valethi Technologies
	 * @Description : Use Below Method to get Random Number for Specific Range
	 */
	public int getRandomNumbersWithinRange(int minimumLimt, int maximumLimit) {
		try {
			Random rand = new Random();
			return rand.nextInt((maximumLimit - minimumLimt) + 1) + minimumLimt;
		} catch (Exception exception) {
			objPojo.setCustomException("Developer Side Issue");
			exception.printStackTrace();
			return 0;
		}
	}

	/*
	 *
	 * NOTE : If you want future date : Pass No of Future day 's value as positive
	 * int i.e. 5 If you want Back date : Pass No of Future day 's value as Negative
	 * . i.e. -5
	 *
	 * @Method : getFutureDateInSpecifiedFormat
	 *
	 * @Description : This method takes parameter of your required DateFormat Type
	 * Like: dd-mm-YYYY OR DD.MM.YYYY and in return it will give you Future date in
	 * specified date format
	 *
	 * @param1 : dateFormat like : dd-MM-YYYY
	 *
	 * @param2 : No Of Future Day from now
	 *
	 * @Author : Uday Naik - Valethi Technologies
	 *
	 * i.e. : if you call getFutureDateInSpecifiedFormat("DD-MM-YYYY",5) then it
	 * will return future date (current date + next 5 days)
	 *
	 * NOTE : If you want future date : Pass No of Future day 's value as positive
	 * int i.e. 5 If you want Back date : Pass No of Future day 's value as Negative
	 * . i.e. -5
	 *
	 */
	public String getFutureOrBackDateInSpecifiedFormat(String dateFormat, int NoOfFutureDay) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, NoOfFutureDay);
		String futureDate = new SimpleDateFormat(dateFormat).format(c.getTime()).toString();
		return futureDate;
	}

	/**
	 * @Method: dpString
	 * @Description: this method returns data from the the previously loaded
	 *               datapool
	 * @param columnHeader - excel file header column name
	 * @return - value for corresponding header
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public String dpString(String columnHeader) {
		try {
			if (objPojo.getObjHashTable().get(columnHeader) == null) {
				return "";
			} else {
				return objPojo.getObjHashTable().get(columnHeader).trim();
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * @Method: dpInteger
	 * @Description: this method returns data from the the previously loaded
	 *               datapool
	 * @param columnHeader - excel file header column name
	 * @return - value for corresponding header
	 * @Author : Uday Naik - Valethi Technologies
	 *
	 */
	public Integer dpInteger(String columnHeader) {
		try {
			if (objPojo.getObjHashTable().get(columnHeader) == null) {
				return 0;
			} else {
				String value = objPojo.getObjHashTable().get(columnHeader).trim();
				StringTokenizer strTokenizer = new StringTokenizer(value, ".");
				value = strTokenizer.nextToken();
				return Integer.parseInt(value);
			}
		} catch (NoSuchElementException exception) {
			return 0;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * @Method: setDataPool
	 * @Description: this method returns data from the the previously loaded
	 *               setDataPool
	 * @param columnHeader - excel file header column name
	 * @return - value for corresponding header
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public void setDataPool(String Key, String value) {
		try {
			objPojo.getObjHashTable().put(Key, value);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * @Method: isFileDownloaded
	 * @Description: This method return true or false for file downloaded or not.
	 *
	 * @param expectedfileName - fileExtention - folderPath - timeout
	 * @return - True or False
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public static boolean isFileDownloaded(String expectedfileName, String fileExtention, String folderPath,
			int timeout) {

		String folderName = folderPath;
		File[] listOfile;
		String fileName1;
		boolean fileDownloaded = false;
		long startTime = Instant.now().toEpochMilli();
		long waitTime = startTime + timeout;
		while (Instant.now().toEpochMilli() < waitTime) {

			listOfile = new File(folderName).listFiles();

			for (File file : listOfile) {
				fileName1 = file.getName().toLowerCase();
				if (fileName1.contains(expectedfileName.toLowerCase())
						&& fileName1.contains(fileExtention.toLowerCase()) && !fileName1.contains("crdownload")
						&& file.lastModified() < startTime) {
					System.out.println(fileName1);
					fileDownloaded = true;
					break;
				}
			}
			if (fileDownloaded) {
				break;
			}
		}
		return fileDownloaded;
	}

	public static void updateExcelSheet(String folderPath, String username, String name, String email, String company,
			String telephone, String country, String zipcode, String city, String Address, String sapuserid) {

		String sysUserNaem = System.getProperty("user.name");
		folderPath = "C:\\Users\\" + sysUserNaem + "\\Downloads";
		File excelPath = getLatestFilefromDir(folderPath);
		// Files.readString(excelPath);

		System.out.println(excelPath);
		Fillo fillo = new Fillo();
		Connection connection;
		try {
			connection = fillo.getConnection(excelPath.toString());

			String strInsertQuerry = " INSERT INTO Table1 (USERNAME,NAME,EMAIL,COMPANYNAME,TELEPHONE,COUNTRY,ZIPCODE,CITY,ADDRESS,SAPUSERID) Values('"
					+ username + "','" + name + "','" + email + "','" + company + "','" + telephone + "','" + country
					+ "','" + zipcode + "','" + city + "','" + Address + "','" + sapuserid + "')";
			connection.executeUpdate(strInsertQuerry);
			connection.close();
		} catch (FilloException e) {
			e.printStackTrace();
		}

	}

	public static File getLatestFilefromDir(String dirPath) {

		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	/**
	 * @Method: apiPriceValues
	 * @Description: This method return price Values of Shipping.
	 *
	 * @param expectedfileName - fileExtention - folderPath - timeout
	 * @return - Price Values
	 * @Author : Uday Naik - Valethi Technologies
	 */

	public static Map<String, String> apiPriceValues(String data) {

		Map<String, String> price = new HashMap<>();

		JSONObject jsonObj = new JSONObject(data);

		JSONArray ja = jsonObj.getJSONArray("SHIPPING_METHOD_NAMES");

		for (int i = 0; i < ja.length(); i++) {

			JSONObject explrObject = ja.getJSONObject(i);
			if (i == 0) {
				price.put("UPS_Ground", (String) explrObject.get("PRICE"));
			}
			if (i == 1) {
				price.put("UPS_Second_Day_Air", (String) explrObject.get("PRICE"));
			}
			if (i == 2) {
				price.put("UPS_Next_Day_Air", (String) explrObject.get("PRICE"));
			}

		}
		return price;

	}

	public static Map<String, String> apiPriceValuesByName(String data) {

		Map<String, String> price = new HashMap<>();

		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("NAME", "UPS Second Day Air");
		jsonObj.put("NAME", "UPS Next Day Air");
		jsonObj.put("NAME", "UPS Ground");

		JSONArray array = jsonObj.getJSONArray("SHIPPING_METHOD_NAMES");

		for (int i = 0; i <= array.length() - 1; i++) {
			JSONObject explore = array.getJSONObject(i);
			String text = (String) explore.get("NAME");

			if (text.contains("UPS Second Day Air")) {
				System.out.println("UPS Second Day Air");
				String priceByName = (String) explore.get("PRICE");
				System.out.println(priceByName);
				price.put("UPSSecondDayAir", priceByName);
			} else if (text.contains("UPS Next Day Air")) {
				System.out.println("UPS Next Day Air");
				String priceByName = (String) explore.get("PRICE");
				System.out.println(priceByName);
				price.put("UPSNextDayAir", priceByName);
			}

			else if (text.contains("UPS Ground")) {
				System.out.println("UPS Ground");
				String priceByName = (String) explore.get("PRICE");
				System.out.println(priceByName);
				price.put("UPSGround", priceByName);
			}

		}
		return price;

	}

	public String getTodayDateWithdateFormat(String format) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String str = formatter.format(date);

		return str;

	}

	/**
	 * @Method : extractNumberFromString
	 * @Description : This method used to extract the number from string.
	 * @param : input
	 *
	 * @return : -
	 * @author : Dhanshree Swami
	 * @created on : 08-08-2023
	 */

	public String extractNumberFromString(String input) {
		int count= input.length();
	       char c;
	       String number="";
	       for(int i=1;i<count;i++) {
	    	   c=input.charAt(i);

	    	   if (Character.isDigit(c)) {

	    		   number=number+c;
	             }

	       }
	       return number;
	}

}
