package com.generics;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Pojo {
	private WebDriver webDriver;
	private WebDriverWait webDriverWait;
	private Properties objConfig;
	private Hashtable<String, String> dataPoolHashTable;
	private Utilities objUtilities;
	private WrapperFunctions objWrapperFunctions;
	private CustomReporter objReporter;
	private String runningScript = "";
	private String strTestDataFilePath = "";
	private String testCaseID = "";
	private int runID;
	private int afterClickwait = 0;
	private int scriptTimeoutWait = 0;
	private boolean executionFlag;
	private Connection conn = null;
	private Statement stmt = null;
	private String exceptionFlag = "";
	private int testDataRow;
	private String portalURL = "";
	private Hashtable<String, String> objHashTable = new Hashtable<>();
	private XSSFWorkbook workbook;
	private String TCID;
	public static String reportPath = "";


	public int getScriptTimeoutWait() {
		return this.scriptTimeoutWait;
	}

	public void setScriptTimeoutWait(int scriptTimeoutWait) {
		this.scriptTimeoutWait = scriptTimeoutWait;
	}

	public Integer getAfterClickwait() {
		return this.afterClickwait;
	}

	public void setAfterClickwait(Integer afterClickwait) {
		this.afterClickwait = afterClickwait;
	}

	public void setDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public WebDriver getDriver() {
		return this.webDriver;
	}

	public String getStrTestDataFilePath() {
		return this.strTestDataFilePath;
	}

	public void setStrTestDataFilePath(String strTestDataFilePath) {
		this.strTestDataFilePath = strTestDataFilePath;
	}

	public void setWebDriverWait(WebDriverWait webDriverWait) {
		this.webDriverWait = webDriverWait;
	}

	public WebDriverWait getWebDriverWait() {
		return this.webDriverWait;
	}

	public void setObjConfig(Properties objConfig) {
		this.objConfig = objConfig;
	}

	public Properties getObjConfig() {
		return this.objConfig;
	}

	public void setDataPoolHashTable(Hashtable<String, String> dataPoolHashTable) {
		this.dataPoolHashTable = dataPoolHashTable;
	}

	public Hashtable<String, String> getDataPoolHashTable() {
		return this.dataPoolHashTable;
	}

	public void setRunningScriptName(String scriptName) {
		this.runningScript = scriptName;
	}

	public String getRunningScriptName() {
		return this.runningScript;
	}

	public void setCustomeReporter(CustomReporter reporter) {
		this.objReporter = reporter;
	}

	public CustomReporter getCustomReporter() {
		return this.objReporter;
	}

	public Utilities getObjUtilities() {
		return this.objUtilities;
	}

	public void setObjUtilities(Utilities objUtilities) {
		this.objUtilities = objUtilities;
	}

	public WrapperFunctions getObjWrapperFunctions() {
		return this.objWrapperFunctions;
	}

	public void setObjWrapperFunctions(WrapperFunctions objWrapperFunctions) {
		this.objWrapperFunctions = objWrapperFunctions;
	}

	public String getTestCaseID() {
		return this.testCaseID;
	}

	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}

	public boolean getExecutionFlag() {
		return this.executionFlag;
	}

	public void setExecutionFlag(boolean executionFlag) {
		this.executionFlag = executionFlag;
	}

	public int getRunID() {
		return this.runID;
	}

	public void setRunID(int runID) {
		this.runID = runID;
	}

	public Connection getDataBaseConnection() {
		return conn;
	}

	public void setDataBaseConnection(Connection conn) {
		this.conn = conn;
	}

	public Statement getDataBaseStatement() {
		return stmt;
	}

	public void setDataBaseStatement(Statement stmt) {
		this.stmt = stmt;
	}

	public String getCustomException() {
		return exceptionFlag;
	}

	public void setCustomException(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}

	public int getTestDataRow() {
		return testDataRow;
	}

	public void setTestDataRow(int testDataRow) {
		this.testDataRow = testDataRow;
	}

	public String getPortalURL() {
		return portalURL;
	}

	public void setPortalURL(String portalURL) {
		this.portalURL = portalURL;
	}

	public void setRunningTestName(String methodName) {
		TCID = methodName;
	}

	public String getRunninTestName() {
		return TCID;
	}

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public Hashtable<String, String> getObjHashTable() {
		return objHashTable;
	}

	public void setObjHashTable(Hashtable<String, String> objHashTable) {
		this.objHashTable = objHashTable;
	}

}