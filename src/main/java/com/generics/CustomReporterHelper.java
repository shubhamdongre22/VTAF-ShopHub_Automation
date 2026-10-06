package com.generics;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Recordset;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class CustomReporterHelper extends Pojo {
	String strExcelFilePath = "";
	private XSSFWorkbook workbook;
	private XSSFSheet summarySheet;
	private XSSFRow summaryCurrentRow;
	int intSummarySheetRowCounter = 0;
	private XSSFSheet testResultSheet;
	private XSSFRow testResultSheetCurrentRow;
	int intTestResultRowCounter = 0;
	int intSkipSheetRowCounter = 0;
	String developer = "Uday Naik";
	String customError = "";
	Pojo objPojo;
	private Hashtable<String, String> objHashTable;

	// Azure API details
	static int testCaseIdPoint;
	static int runId;
	static int resultUpdate;



	synchronized public void startExcelReport(ITestResult result, String status) {

		try {
			Object[] res = result.getParameters();
			for (int i = 0; i < res.length; i++) {
				System.out.println(res.toString());
			}
			String moduleName = result.getTestContext().getName();
			String completeTestClassPath = result.getTestClass().getName();
			String packageName = completeTestClassPath.substring(0, completeTestClassPath.lastIndexOf("."));
			String testClassName = result.getTestClass().getRealClass().getSimpleName();
			String methodName = result.getMethod().getMethodName();
			double executionTime = (result.getEndMillis() - result.getStartMillis()) / 1000.0D;
			objPojo = (Pojo) result.getInstance();
			String TCID = objPojo.getTestCaseID();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String reportDate = dateFormat.format(date).toString();
			HashMap<String, String>  objInputHashTable;
			objInputHashTable=pickUpAPIdetails(methodName,objPojo.getObjConfig().getProperty("testSuitePath"));
			if (strExcelFilePath.equals("")) {
				this.strExcelFilePath = Pojo.reportPath + "/custom-reports/ExcelReports_" + reportDate + ".xlsx";
			}
			File reportFile = new File(this.strExcelFilePath);
			System.out.println(reportFile);
			String exception;
			if (!reportFile.exists()) {
				// (new File(objPojo.getReportPath() + "/custom-reports")).mkdirs();
				reportFile.createNewFile();
				exception = System.getProperty("user.dir")
						+ "\\src\\main\\resources\\externalResources\\Custom_Design_Files\\Excel\\reportTemplate.xlsx";
				this.workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(exception));
				this.summarySheet = this.workbook.getSheet("TestSummary");
				this.summaryCurrentRow = this.summarySheet.getRow(1);
				this.testResultSheet = this.workbook.createSheet("TestResult");
				this.testResultSheetCurrentRow = this.testResultSheet.createRow(0);
				this.createHeaderRow(this.testResultSheetCurrentRow);
			} else {
				this.workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(this.strExcelFilePath));
				this.summarySheet = this.workbook.getSheet("TestSummary");
				this.summaryCurrentRow = this.summarySheet.getRow(1);
				this.testResultSheet = this.workbook.getSheet("TestResult");
			}
			this.workbook.setSheetOrder("TestResult", 0);
			this.workbook.setSheetOrder("TestSummary", 1);
			exception = "";
			if (status.equalsIgnoreCase("pass") || status.equalsIgnoreCase("fail")
					|| status.equalsIgnoreCase("skipped")) {
				this.intTestResultRowCounter = this.testResultSheet.getLastRowNum() + 1;
				this.testResultSheetCurrentRow = this.testResultSheet.createRow(this.intTestResultRowCounter);
				if (status.equalsIgnoreCase("fail")) {
					exception = result.getThrowable().getClass().getName() + ":" + result.getThrowable().getMessage();
					System.out.println("exception" + exception);
					System.out.println("Exception for +" + TCID + " - " + exception);
					System.out.println("Exception for M +" + TCID + " - " + result.getThrowable().getMessage());
				}
				if (status.equalsIgnoreCase("skipped")) {
					exception = "Pre-Condition Failed";
				}
				this.addExecutionResultInTestResultSheet(TCID, packageName, testClassName, methodName, exception,
						String.valueOf(executionTime), status, moduleName, objInputHashTable.get("DevloperName"), this.customError,objInputHashTable.get("Module"),objInputHashTable.get("Tenants"));
			}
			this.updateSummarySheet(status);
			this.endExcelReport();
			//########Azure API Calling###############

			//String testData=objPojo.getobjConfigManager().dpString("UserName");

			//objPojo.getObjUtilities().updateExcelSheet(exception, status, methodName, moduleName, completeTestClassPath, packageName, testClassName, methodName, TCID, reportDate, exception);

			try {
			//	HashMap<String, String>  	objInputHashTable;

			//objInputHashTable=pickUpAPIdetails(methodName,objPojo.getObjConfig().getProperty("testSuitePath"));
			String postUrl="https://dev.azure.com/"+objPojo.getObjConfig().getProperty("organization_name")+"/"+objInputHashTable.get("Project_id")+"/_apis/test/runs?api-version=7.0";
			String userName=objPojo.getObjConfig().getProperty("AzureApiUser");
			String pass=objPojo.getObjConfig().getProperty("AzurePass");

			String [] str = methodName.split("_");
			String testCaseId = objInputHashTable.get("TestCaseId");
			if(status.equals("Pass")) {
			status="PASSED";
			String testCaseResult=status;
			testCaseIdPoint=getRequest(objPojo.getObjConfig().getProperty("organization_name"),objInputHashTable.get("Project_id"),userName,pass,objInputHashTable.get("PlanID"),objInputHashTable.get("SuiteId"),testCaseId);
			//System.out.println(testCaseIdPoint);
			runId=postRequest(postUrl,userName,pass,objInputHashTable.get("PlanID"),objInputHashTable.get("PlanName"),testCaseIdPoint);
			resultUpdate=getRequestResult(objPojo.getObjConfig().getProperty("organization_name"),objInputHashTable.get("Project_id"),userName,pass,Integer.toString(runId));
			patchResultUpdate(objPojo.getObjConfig().getProperty("organization_name"),objInputHashTable.get("Project_id"),userName,pass,resultUpdate,testCaseResult,runId);
		runUpdated(objPojo.getObjConfig().getProperty("organization_name"),objInputHashTable.get("Project_id"),userName,pass,runId);
		}

			if(status.equals("Fail")) {
				status="FAILED";
				String testCaseResult=status;
				testCaseIdPoint=getRequest(objPojo.getObjConfig().getProperty("organization_name"),objInputHashTable.get("Project_id"),userName,pass,objInputHashTable.get("PlanID"),objInputHashTable.get("SuiteId"),testCaseId);
				//System.out.println(testCaseIdPoint);
				runId=postRequest(postUrl,userName,pass,objInputHashTable.get("PlanID"),objInputHashTable.get("PlanName"),testCaseIdPoint);
				resultUpdate=getRequestResult(objPojo.getObjConfig().getProperty("organization_name"),objInputHashTable.get("Project_id"),userName,pass,Integer.toString(runId));
				patchResultUpdate(objPojo.getObjConfig().getProperty("organization_name"),objInputHashTable.get("Project_id"),userName,pass,resultUpdate,testCaseResult,runId);
				runUpdated(objPojo.getObjConfig().getProperty("organization_name"),objInputHashTable.get("Project_id"),userName,pass,runId);
				}
			}catch (Exception e) {
				System.out.println("Error At Azure api Implementation Code ===========>>> "+methodName +" "+e);
			}

		} catch (Exception var18) {
			var18.printStackTrace();
		}

	}

	public void createHeaderRow(XSSFRow headerRow) {
		this.createHeaderCell(headerRow, 0, "Sr. No.");
		this.createHeaderCell(headerRow, 1, "Time Stamp");
		this.createHeaderCell(headerRow, 2, "TCID");
		this.createHeaderCell(headerRow, 3, "Module");
		this.createHeaderCell(headerRow, 4, "Class Name");
		this.createHeaderCell(headerRow, 5, "MethodName");
		this.createHeaderCell(headerRow, 6, "Result");
		this.createHeaderCell(headerRow, 7, "Exception");
		this.createHeaderCell(headerRow, 8, "Execution Time");
		this.createHeaderCell(headerRow, 9, "Module Name");
		this.createHeaderCell(headerRow, 10, "Developer");
		this.createHeaderCell(headerRow, 11, "Custom Error");
		this.createHeaderCell(headerRow, 12, "Module");
		this.createHeaderCell(headerRow, 13, "Tenants");
	}

	public void addExecutionResultInTestResultSheet(String TCID, String module, String className, String methodName,
			String exception, String executionTime, String result, String moduleName, String developer,
			String customError,String Module, String Tenants) {
		this.createSheetCellRightAlliged(this.testResultSheetCurrentRow, 0,
				String.valueOf(this.intTestResultRowCounter));
		this.createSheetCellCenterAlliged(this.testResultSheetCurrentRow, 1, this.getCurrentDateTime());
		this.createSheetCellCenterAlliged(this.testResultSheetCurrentRow, 2, TCID);
		this.createSheetCell(this.testResultSheetCurrentRow, 3, module);
		this.createSheetCell(this.testResultSheetCurrentRow, 4, className);
		this.createSheetCell(this.testResultSheetCurrentRow, 5, methodName);
		this.createResultCell(this.testResultSheetCurrentRow, 6, result);
		this.createSheetCell(this.testResultSheetCurrentRow, 7, exception);
		this.createSheetCellCenterAlliged(this.testResultSheetCurrentRow, 8, executionTime);
		this.createSheetCellCenterAlliged(this.testResultSheetCurrentRow, 9, moduleName);
		this.createSheetCellCenterAlliged(this.testResultSheetCurrentRow, 10, developer);
		this.createSheetCellCenterAlliged(this.testResultSheetCurrentRow, 11, objPojo.getCustomException());
		objPojo.setCustomException("");
		this.createSheetCellCenterAlliged(this.testResultSheetCurrentRow, 12, Module);
		this.createSheetCellCenterAlliged(this.testResultSheetCurrentRow, 13, Tenants);
	}

	public void updateSummarySheet(String status) {
		int skippedCount;
		if (status.equalsIgnoreCase("pass")) {
			skippedCount = (int) this.summaryCurrentRow.getCell(0).getNumericCellValue() + 1;
			this.createSheetCellCenterAlligedNumeric(this.summaryCurrentRow, 0, String.valueOf(skippedCount));
		} else if (status.equalsIgnoreCase("fail")) {
			skippedCount = (int) this.summaryCurrentRow.getCell(1).getNumericCellValue() + 1;
			this.createSheetCellCenterAlligedNumeric(this.summaryCurrentRow, 1, String.valueOf(skippedCount));
		} else if (status.equalsIgnoreCase("skipped")) {
			skippedCount = (int) this.summaryCurrentRow.getCell(2).getNumericCellValue() + 1;
			this.createSheetCellCenterAlligedNumeric(this.summaryCurrentRow, 2, String.valueOf(skippedCount));
		}

		skippedCount = (int) this.summaryCurrentRow.getCell(3).getNumericCellValue() + 1;
		this.createSheetCellCenterAlligedNumeric(this.summaryCurrentRow, 3, String.valueOf(skippedCount));
	}

	public void endExcelReport() throws IOException {
		this.autoSetColumnWidth();
		FileOutputStream fileOutputStream = new FileOutputStream(this.strExcelFilePath);
		this.workbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public void createSheetCell(XSSFRow row, int cellNumber, String value) {
		XSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(this.getCellStyleLeftAlliged());
	}

	public void createSheetCellCenterAlliged(XSSFRow row, int cellNumber, String value) {
		XSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(this.getCellStyleCenterAlliged());
	}

	public void createSheetCellCenterAlligedNumeric(XSSFRow row, int cellNumber, String value) {
		XSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(Integer.parseInt(value));
		cell.setCellStyle(this.getCellStyleCenterAlliged());
	}

	public void createSheetCellRightAlliged(XSSFRow row, int cellNumber, String value) {
		XSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(this.getCellStyleRightAlliged());
	}

	public void createSheetPassStepCell(XSSFRow row, int cellNumber) {
		XSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString("PASS"));
		cell.setCellStyle(this.getPassCellStyle());
	}

	public void createResultCell(XSSFRow row, int cellNumber, String value) {
		XSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		System.out.println("Result ****************:     " + value.toLowerCase().trim());
		String var5 = value.toLowerCase().trim();
		byte var6 = -1;
		switch (var5.hashCode()) {
		case -1988746795:
			if (var5.equals("fail - Application Side Issue")) {
				var6 = 3;
			}
			break;
		case -1643861444:
			if (var5.equals("fail - Application Freeze")) {
				var6 = 2;
			}
			break;
		case 3135262:
			if (var5.equals("fail")) {
				var6 = 1;
			}
			break;
		case 3433489:
			if (var5.equals("pass")) {
				var6 = 0;
			}
			break;
		case 154432742:
			if (var5.equals("fail - Tool Limitations")) {
				var6 = 7;
			}
			break;
		case 1534758069:
			if (var5.equals("fail - Assertion Error")) {
				var6 = 4;
			}
			break;
		case 1872143336:
			if (var5.equals("fail - UI Change/Element Changed")) {
				var6 = 5;
			}
			break;
		case 1879267419:
			if (var5.equals("fail - Developer Side Issue")) {
				var6 = 6;
			}
			break;
		case 2147444528:
			if (var5.equals("skipped")) {
				var6 = 8;
			}
		}

		switch (var6) {
		case 0:
			cell.setCellStyle(this.getPassCellStyle());
			break;
		case 1:
		case 2:
			cell.setCellStyle(this.getFailCellStyle());
			break;
		case 3:
			cell.setCellStyle(this.getFailCellStyle());
			break;
		case 4:
			cell.setCellStyle(this.getFailCellStyle());
			break;
		case 5:
			cell.setCellStyle(this.getFailCellStyle());
			break;
		case 6:
			cell.setCellStyle(this.getSeleniumSideFailCellStyle());
			break;
		case 7:
			cell.setCellStyle(this.getSeleniumSideFailCellStyle());
			break;
		case 8:
			cell.setCellStyle(this.getSkippedCellStyle());
		}

	}

	public void createHeaderCell(XSSFRow row, int cellNumber, String value) {
		XSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new XSSFRichTextString(value));
		cell.setCellStyle(this.getHeaderCellStyle());
	}

	// public void createWelcomeHeaderCell(XSSFRow row, int cellNumber, String
	// value) {
	// XSSFCell cell = row.createCell(cellNumber);
	// cell.setCellValue(new XSSFRichTextString(value));
	// cell.setCellStyle(this.getWelcomeCellStyle());
	// }

	private XSSFCellStyle getCellStyleLeftAlliged() {
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setAlignment((short) 1);
		return cellStyle;
	}

	private XSSFCellStyle getCellStyleCenterAlliged() {
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setAlignment((short) 2);
		return cellStyle;
	}

	private XSSFCellStyle getCellStyleRightAlliged() {
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setAlignment((short) 3);
		return cellStyle;
	}

	private XSSFCellStyle getPassCellStyle() {
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setAlignment((short) 2);
		XSSFColor myColor = new XSSFColor(Color.GREEN);
		cellStyle.setFillForegroundColor(myColor);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	private XSSFCellStyle getFailCellStyle() {
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setAlignment((short) 2);
		XSSFColor myColor = new XSSFColor(Color.RED);
		cellStyle.setFillForegroundColor(myColor);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	private XSSFCellStyle getSeleniumSideFailCellStyle() {
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setAlignment((short) 2);
		XSSFColor myColor = new XSSFColor(Color.ORANGE);
		cellStyle.setFillForegroundColor(myColor);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	private XSSFCellStyle getSkippedCellStyle() {
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setAlignment((short) 2);
		XSSFColor myColor = new XSSFColor(Color.LIGHT_GRAY);
		cellStyle.setFillForegroundColor(myColor);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	private XSSFCellStyle getHeaderCellStyle() {
		XSSFFont headerFont = this.workbook.createFont();
		headerFont.setBoldweight((short) 700);
		headerFont.setFontName("Arial");
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		cellStyle.setAlignment((short) 2);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setFillForegroundColor((short) 22);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	public XSSFCellStyle getWelcomeCellStyle() {
		XSSFFont headerFont = this.workbook.createFont();
		headerFont.setBoldweight((short) 700);
		headerFont.setFontName("Arial");
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setFont(headerFont);
		cellStyle.setAlignment((short) 2);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setFillPattern((short) 1);
		XSSFColor myColor = new XSSFColor(Color.GREEN);
		cellStyle.setFillForegroundColor(myColor);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	// public void createSummaryLinkCell(XSSFRow row, int cellNumber, String value,
	// String nevigationSheet) {
	// XSSFCell cell = row.createCell(cellNumber);
	// cell.setCellValue(new XSSFRichTextString(value));
	// CreationHelper createHelper = this.workbook.getCreationHelper();
	// Hyperlink link = createHelper.createHyperlink(2);
	// link.setAddress("'" + nevigationSheet + "'!A1");
	// cell.setHyperlink(link);
	// cell.setCellStyle(this.getHlinkCellStyle());
	// }

	public XSSFCellStyle getHlinkCellStyle() {
		XSSFCellStyle cellStyle = this.workbook.createCellStyle();
		Font hlink_font = this.workbook.createFont();
		hlink_font.setUnderline((byte) 1);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_font.setBoldweight((short) 700);
		hlink_font.setFontName("Arial");
		cellStyle.setFont(hlink_font);
		cellStyle.setAlignment((short) 2);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setFillForegroundColor((short) 22);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	private String getCurrentDateTime() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		return sdfDate.format(now);
	}

	public void autoSetColumnWidth() {
		this.testResultSheet.autoSizeColumn(0);
		this.testResultSheet.autoSizeColumn(1);
		this.testResultSheet.autoSizeColumn(2);
		this.testResultSheet.autoSizeColumn(3);
		this.testResultSheet.autoSizeColumn(4);
		this.testResultSheet.autoSizeColumn(5);
		this.testResultSheet.autoSizeColumn(6);
		this.testResultSheet.autoSizeColumn(7);
		this.testResultSheet.autoSizeColumn(8);
		this.testResultSheet.autoSizeColumn(9);
		this.testResultSheet.autoSizeColumn(10);
		this.testResultSheet.autoSizeColumn(11);
		this.testResultSheet.autoSizeColumn(12);
		this.testResultSheet.autoSizeColumn(13);
	}


			public static int getRequest(String organization_name,String project_id, String userName,String pass, String planId,String suiteId,String testCaseId) {
				String apiUrl="https://dev.azure.com/"+organization_name+"/"+project_id+"/_apis/test/Plans/"+planId+"/Suites/"+suiteId+"/points?api-version=7.0&testCaseId="+testCaseId+"";
				int responceCode;
				int testCasepointId;
				String actualResponceCode="200";
				RequestSpecification httpRequest = RestAssured.given().auth().preemptive().basic(userName,pass);
				Response response = httpRequest.get(apiUrl);
				ResponseBody body = response.getBody();
				String bodyStringValue = body.asString();
				responceCode= response.getStatusCode();

				JSONObject jsonObj = new JSONObject(bodyStringValue);
				JSONArray ja = jsonObj.getJSONArray("value");
				JSONObject explrObject = ja.getJSONObject(0);
				testCasepointId=(int) explrObject.get("id");
				jsonObj=null;
				ja=null;
				response=null;
				body=null;
				explrObject=null;
				return testCasepointId;

			}

			public static  int postRequest(String postUrl,String userName,String pass,String planId,String planName,int testCaseIdPoint ) {
				int runId;
				JSONObject jsonObj = new JSONObject();
				JSONObject jsonObj1 = new JSONObject();
				JSONArray array = new JSONArray();
				array.put(testCaseIdPoint);
				jsonObj1.put("id", planId);
				jsonObj.put("name", planName);
				jsonObj.put("plan",jsonObj1);
				jsonObj.put("pointIds", array);
				jsonObj.put("automated", true);
				jsonObj.put("type", "Web");

				Response response = RestAssured.given().auth().preemptive().basic(userName, pass)
			            .contentType("application/json")
			            .body(jsonObj.toString())
			            .post(postUrl);

				Reporter.log("Post Request Responce Code =============>>>>    ", response.getStatusCode());
			    String responseBody = response.getBody().asString();
			    //System.out.println(responseBody);
			    JSONObject responseJsonObj = new JSONObject(responseBody);
			    //System.out.println(responseJsonObj.get("id"));
			    runId=(int) responseJsonObj.get("id");

			    jsonObj=null;
			    jsonObj1=null;
			    array=null;
			    responseJsonObj=null;
			    response=null;
				return runId;

			}

		public static  int getRequestResult(String organization_name,String project_id,String userName,String pass,String runId) {
			String resultApiUrl="https://dev.azure.com/"+organization_name+"/"+project_id+"/_apis/test/Runs/"+runId+"/results?api-version=7.0";
			int testCasepointId = 0;
			int responceCode=0;
			RequestSpecification httpRequest = RestAssured.given().auth().preemptive().basic(userName,pass);
			Response response = httpRequest.get(resultApiUrl);
			ResponseBody body = response.getBody();
			String bodyStringValue = body.asString();
			Reporter.log("Get Result Request Responce Code =============>>>>    ", response.getStatusCode());
			JSONObject jsonObj = new JSONObject(bodyStringValue);
			JSONArray ja = jsonObj.getJSONArray("value");
			JSONObject explrObject = ja.getJSONObject(0);
			testCasepointId=(int) explrObject.get("id");
			//System.out.println(testCasepointId);
			response=null;
			body=null;
			jsonObj=null;
			ja=null;
			explrObject=null;
			return testCasepointId;


		}

		public static void patchResultUpdate(String organization_name,String project_id,String userName,String pass,int resultId,String result,int runId) {
			String patchUrl="https://dev.azure.com/"+organization_name+"/"+project_id+"/_apis/test/Runs/"+runId+"/results?api-version=7.0";
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", resultId);
			jsonObj.put("outcome", result);
			jsonObj.put("state", "Completed");
			jsonObj.put("comment", "Execution Successful");
			JSONArray array = new JSONArray();
			array.put(jsonObj);
			//System.out.println(array);

			Response response = RestAssured.given().auth().preemptive().basic(userName, pass)
		            .contentType("application/json")
		            .body(array.toString())
		            .patch(patchUrl);
			Reporter.log("Patch Result Update Responce Code =============>>>>    ", response.getStatusCode());
			String responseBody = response.getBody().asString();
		  //  System.out.println(responseBody);
			//System.out.println(response.getStatusCode());
			jsonObj=null;
			array=null;
			response=null;
			response=null;
		}

		public static void runUpdated(String organization_name,String project_id,String userName,String pass,int runID) {

			String patchUrl="https://dev.azure.com/"+organization_name+"/"+project_id+"/_apis/test/Runs/"+runID+"?api-version=7.0";
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("state", "Completed");

			Response response = RestAssured.given().auth().preemptive().basic(userName, pass)
		            .contentType("application/json")
		            .body(jsonObj.toString())
		            .patch(patchUrl);
			Reporter.log("Patch Run Update Responce Code =============>>>>    ", response.getStatusCode());
			String responseBody = response.getBody().asString();
		   // System.out.println(responseBody);
			//System.out.println(response.getStatusCode());
			response=null;
			jsonObj=null;

		}

		@SuppressWarnings("null")
		public  HashMap<String, String>  pickUpAPIdetails(String testCaseId,String path) {

			 HashMap<String, String> excelhashmapvalues = new HashMap<>();
			 com.codoid.products.fillo.Fillo fil_res = new com.codoid.products.fillo.Fillo();
		        Connection connect = null;
		        Recordset recordset = null;
				try {
					connect = fil_res.getConnection(path);

		        String query = "SELECT * FROM TestScenarios where Reference='"+testCaseId+"' ";

					recordset = connect.executeQuery(query);
					while(recordset.next())
					{
					    ArrayList<String> collection = recordset.getFieldNames();
					    int size = collection.size();
					    for (int i = 0; i <=(size-1); i++) {
					        String colname = collection.get(i);
					        String colval = null;
							try {
								colval = recordset.getField(colname);
							} catch (FilloException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					        excelhashmapvalues.put(colname, colval);

					    }

					}

				} catch (FilloException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        recordset.close();
		        connect.close();
		        return excelhashmapvalues;

		}

}