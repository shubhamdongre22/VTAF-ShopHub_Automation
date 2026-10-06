package com.generics;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConfigManager {
	public Pojo objPojo;
	private XSSFWorkbook workBook;
	private String TCID = "";
	private Hashtable<String, String> objHashTable;
	private String RunningMethod;
	private String RunningSheetEntity;

	public ConfigManager(Pojo pojo) {
		objPojo = pojo;
		workBook = objPojo.getWorkbook();
		String[] script = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
		RunningSheetEntity = script[script.length - 1];
	}

	/**
	 * @Method: dpString
	 * @Description: this method returns data from the the previously loaded
	 *               datapool
	 * @param columnHeader
	 *            - excel file header column name
	 * @return - value for corresponding header
	 * @Author : Uday Naik - Valethi
	 */
	protected String dpString(String columnHeader) {
		initializeHashTable();
		try {
			if (objHashTable.get(columnHeader) == null) {
				return "";
			} else {
				return objHashTable.get(columnHeader).trim();
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * @Method: dpInteger
	 * @Description: this method returns data from the the previously loaded
	 *               datapool
	 * @param columnHeader
	 *            - excel file header column name
	 * @return - value for corresponding header
	 * @Author : Uday Naik - Valethi
	 *
	 */
	protected Integer dpInteger(String columnHeader) {
		initializeHashTable();
		try {
			if (objHashTable.get(columnHeader) == null) {
				return 0;
			} else {
				String value = objHashTable.get(columnHeader).trim();
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

	protected void initializeHashTable() {

	    RunningMethod = Thread.currentThread().getStackTrace()[4].getMethodName();

	    String[] script = Thread.currentThread()
	            .getStackTrace()[3].getClassName().split("\\.");

	    String RunningSheet = script[script.length - 1];

	    if (!TCID.equals(RunningMethod) || !RunningSheetEntity.equals(RunningSheet)) {

	        TCID = RunningMethod;
	        RunningSheetEntity = RunningSheet;

	        objPojo.setTestCaseID(TCID);

	        XSSFSheet sheet = workBook.getSheet(RunningSheetEntity);

	        if (sheet == null) {
	            throw new RuntimeException(
	                    "Excel sheet not found: " + RunningSheetEntity);
	        }

	        if (sheet.getRow(0) == null) {
	            throw new RuntimeException(
	                    "Header row is missing in sheet: " + RunningSheetEntity);
	        }

	        int lastCellNum = sheet.getRow(0).getLastCellNum();

	        for (int j = 0; j <= sheet.getLastRowNum(); j++) {

	            if (sheet.getRow(j) == null ||
	                    sheet.getRow(j).getCell(0) == null) {
	                continue;
	            }

	            String testCaseID = sheet.getRow(j)
	                    .getCell(0).toString().trim();

	            if (testCaseID.equalsIgnoreCase(TCID)) {

	                objHashTable = new Hashtable<>();

	                for (int i = 0; i < lastCellNum; i++) {

	                    if (sheet.getRow(0).getCell(i) == null) {
	                        continue;
	                    }

	                    String header = sheet.getRow(0)
	                            .getCell(i).toString().trim();

	                    if (sheet.getRow(j).getCell(i) == null) {
	                        objHashTable.put(header, "");
	                        continue;
	                    }

//	                    String value = sheet.getRow(j)
//	                            .getCell(i).toString().trim();
//
//	                    objHashTable.put(header, value);
//	                }
	                    DataFormatter dataFormatter = new DataFormatter();

	                    String value = dataFormatter
	                            .formatCellValue(sheet.getRow(j).getCell(i))
	                            .trim();

	                    objHashTable.put(header, value);
	                }

	                break;
	            }
	        }
	    }
	}
}