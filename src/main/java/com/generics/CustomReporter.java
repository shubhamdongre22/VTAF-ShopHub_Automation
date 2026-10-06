package com.generics;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomReporter extends CustomReporterHelper implements ITestListener {
	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		this.startExcelReport(result, "Pass");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		this.startExcelReport(result, "Fail");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		this.startExcelReport(result, "Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}
}