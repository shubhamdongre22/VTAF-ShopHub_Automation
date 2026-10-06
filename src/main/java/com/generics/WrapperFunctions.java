package com.generics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

/**
 * @ScriptName : WrapperFunctions
 * @Description : Core wrapper function required for framework
 * @Author : Uday Naik - Valethi Technologies
 */
public class WrapperFunctions extends LoadableComponent<WrapperFunctions> {
	private Pojo objPojo;
	Boolean flag = false;

	public WrapperFunctions(Pojo pojo) {
		this.objPojo = pojo;
	}

	/**
	 * @Description : This is wrapper method wait for element presence located
	 * @param :
	 *            locator - By identification of element
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public void waitForElementPresence(By locator) throws NotFoundException {
		objPojo.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * @Description : This is wrapper method wait for visibility of element located
	 * @param :
	 *            locator - By identification of element
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public void waitForElementVisibilityLocated(By locator) throws NotFoundException {
		objPojo.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForElementVisibility(WebElement webElement) throws NotFoundException {
		objPojo.getWebDriverWait().until(ExpectedConditions.visibilityOf(webElement));
	}

	/**
	 * @Description : This is wrapper method wait for element to be clickable
	 * @param :
	 *            locator - By identification of element
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public void waitForElementToBeClickable(By locator) throws NotFoundException {
		objPojo.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(locator));
	}

	/**
	 * @Description : This is wrapper method wait for visibility of element located
	 * @param :
	 *            locator - By identification of element
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public void waitForElementInVisibilityLocated(By locator) throws NotFoundException {
		try {
			objPojo.getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (TimeoutException exception) {

		}
	}

	/**
	 * @Description : This is wrapper method to check the web element is displayed
	 *              on the page
	 * @param :
	 *            locator - By identification of element
	 * @return : - true if element present
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean checkElementDisplayedWithoutWait(By locator) {
		try {
			return objPojo.getDriver().findElement(locator).isDisplayed();
		} catch (Exception exception) {
			return false;
		}
	}

	/*public boolean datePickerForMultipleMonths(By element, String date) {
		try {
			String[] dateArray = date.split("/");
			String month = dateArray[0];
			String dt = dateArray[1];
			String year = dateArray[2];
			month = WordUtils.capitalizeFully(Month.of(Integer.parseInt(month)).name());
			By divCalendarPopup = By.xpath("//div[@id='ui-datepicker-div']");
			if (!this.checkElementDisplayedWithoutWait(divCalendarPopup))
				objPojo.getObjUtilities().logReporter("Click on calendar icon", this.click(element));
			objPojo.getObjUtilities().logReporter("Verify calendar popup displayed",
					this.checkElementDisplayed(divCalendarPopup));
			By lnkMonth = By.xpath(
					"//div[@id='ui-datepicker-div']//div[@class='ui-datepicker-title']/span[text()='" + month + "']");
			By lnkYear = By.xpath(
					"//div[@id='ui-datepicker-div']//div[@class='ui-datepicker-title']/span[text()='" + year + "']");
			By navigator = By.xpath("//div[@id='ui-datepicker-div']//a[@title='Next']");
			while (!checkElementDisplayedWithoutWait(lnkMonth) || !checkElementDisplayedWithoutWait(lnkYear)) {
				objPojo.getDriver().findElement(navigator).click();
			}
			By lnkDate = By.xpath("//div[@id='ui-datepicker-div']//div[@class='ui-datepicker-title']/span[text()='"
					+ month
					+ "']//ancestor::div[starts-with(@class,'ui-datepicker-group ui-datepicker-group')]/table//td[normalize-space()='"
					+ Integer.parseInt(dt) + "']");
			objPojo.getObjUtilities().logReporter("Select date from calendar popup", date, this.click(lnkDate));
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}*\

	public boolean openNewWindow() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) objPojo.getDriver();
			js.executeScript("window.open('','_blank');");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean closeDriver() {
		try {
			objPojo.getDriver().close();
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/** @Author : Uday Naik - Valethi Technologies */
	public void waitAfterEachClick() {
		waitFor(objPojo.getAfterClickwait());
	}

	/**
	 * @Method : waitFor
	 * @Description : Waits for the specified amount of [timeInMilliseconds].
	 * @param :
	 *            timeUnitSeconds - wait time seconds
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean waitFor(int timeUnitSeconds) {
		try {
			Thread.sleep(TimeUnit.MILLISECONDS.convert(timeUnitSeconds, TimeUnit.SECONDS));
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * Scroll element to view using javascript This script is used for scrolling
	 * down.
	 *
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean scrollToViewDown(By locator) {
		try {
			waitForElementPresence(locator);
			WebElement webElement = objPojo.getDriver().findElement(locator);
			((JavascriptExecutor) objPojo.getDriver()).executeScript("arguments[0].scrollIntoView(false);", webElement);
			return true;
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}
	catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method : elementHighlight
	 * @Description : Highlight element
	 * @param :
	 *            locator - By identification of element
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean elementHighlight(By locator) {

		try {
		WebElement element = objPojo.getDriver().findElement(locator);
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) objPojo.getDriver();
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: 3px solid red; border: 3px solid red;");
			waitFor(1);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");

		}
		return true;
	} catch (NoSuchElementException exception) {
		objPojo.setCustomException("Timeout & NoSuchElement Exception");
		System.out.println("I got no such " + exception.getMessage());
		exception.printStackTrace();
		return false;
	} catch (TimeoutException exception) {
		objPojo.setCustomException("Timeout & NoSuchElement Exception");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return false;
	} catch (NotFoundException exception) {
		objPojo.setCustomException("NotFound Exception");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return false;
	}
		catch (ElementNotVisibleException exception) {
		objPojo.setCustomException("ElementNotVisibleException");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return false;
	}
		catch (ElementNotInteractableException exception) {
		objPojo.setCustomException("ElementNotInteractableException Exception");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return false;
	}
		catch (ElementNotSelectableException exception) {
		objPojo.setCustomException("ElementNotSelectableException Exception");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return false;

	}
		catch (Exception exception) {
		objPojo.setCustomException("NoSuchElement Exception");
		exception.printStackTrace();
		return false;
	}


}


	/**
	 * @Method : selectDropDownOption
	 * @Description : This is wrapper method select drop down element
	 * @param :
	 *            locator - By identification of element
	 * @param :
	 *            option - drop down element (user may specify text/value/index)
	 * @param :
	 *            selectType - select dorp down element by Text/Value/Index
	 * @Author : Uday Naik - Valethi Technologies
	 */

	public boolean selectDropDownOption(By locator, String option, String... selectType) {
		try {
			waitForElementPresence(locator);
			WebElement webElement = objPojo.getDriver().findElement(locator);
			waitForElementVisibility(webElement);
			Select sltDropDown = new Select(webElement);
			if (selectType.length > 0 && !selectType[0].equals("")) {
				if (selectType[0].equalsIgnoreCase("Value")) {
					sltDropDown.selectByValue(option);
				} else if (selectType[0].equalsIgnoreCase("Text")) {
					sltDropDown.selectByVisibleText(option);
				} else if (selectType[0].equalsIgnoreCase("Index")) {
					sltDropDown.selectByIndex(Integer.parseInt(option));
				}
				return true;
			} else {
				List<WebElement> options = sltDropDown.getOptions();
				boolean blnOptionAvailable = false;
				int iIndex = 0;
				for (WebElement weOptions : options) {
					if (weOptions.getText().trim().equals(option)) {
						sltDropDown.selectByIndex(iIndex);
						blnOptionAvailable = true;
						break;
					} else {
						iIndex++;
					}
				}
				return blnOptionAvailable;
			}

		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotSelectableException exception) {
			objPojo.setCustomException("ElementNotSelectableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	protected void load() {
	}

	@Override
	protected void isLoaded() throws Error {
	}

	/**
	 * @Author : Uday Naik - Valethi Technologies
	 * @Description : This method is for getting the text on the alert box.
	 */
	public String alertGetText() {
		Alert alert = objPojo.getDriver().switchTo().alert();
		return alert.getText();

	}

	/**
	 * @Method : click
	 * @Description : This is wrapper method to click on web element
	 * @param :
	 *            WebElement -By identification of Locator
	 * @return : - true if click successful
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean click(WebElement element) {
		try {
			waitForElementVisibility(element);
			element.click();
			waitAfterEachClick();
			return true;
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Description : index start with 0 , Hence we have to do "n-1" Also month
	 *              value does not have prefix "0" hence we have to replace "0" with
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean datePicker(By locator, String date) {
		try {
			String[] dateArray = date.split("/");
			String month = dateArray[0];
			int tempIntMonth = 0;
			try {
				tempIntMonth = Integer.parseInt(month);
				if (tempIntMonth >= 1) {
					if (((dateArray[0].startsWith("0")))) {
						tempIntMonth = tempIntMonth - 1;
						if (tempIntMonth == 0) {
							dateArray[0] = tempIntMonth + "";
						} else {
							dateArray[0] = tempIntMonth + "";
							dateArray[0] = dateArray[0].replaceAll("0", "");
						}
					} else {
						tempIntMonth = tempIntMonth - 1;
						dateArray[0] = tempIntMonth + "";
					}
				} else {
					dateArray[0] = tempIntMonth + "";
				}
			} catch (Exception e) {
			}
			objPojo.getObjUtilities().logReporter("Date to Select in Calendar Datepicker", date, true);
			By divCalendarPopup = By.xpath("//div[@id='ui-datepicker-div']");
			By lnkDate = By.xpath(
					"//div[@id='ui-datepicker-div']/table[@class='ui-datepicker-calendar']/tbody/tr/td/a[contains(text(),'"
							+ (dateArray[1].startsWith("0") ? dateArray[1].substring(dateArray[1].indexOf("0") + 1)
									: dateArray[1])
							+ "')]");
			By drpMonth = By.xpath(
					"//div[@id='ui-datepicker-div']/div/div[@class='ui-datepicker-title']/select[@class='ui-datepicker-month']");
			By drpYear = By.xpath(
					"//div[@id='ui-datepicker-div']/div/div[@class='ui-datepicker-title']/select[@class='ui-datepicker-year']");

			objPojo.getObjUtilities().logReporter("Click on calendar icon", this.click(locator));
			objPojo.getObjUtilities().logReporter("Verify calendar popup displayed",
					this.checkElementDisplayed(divCalendarPopup));
			objPojo.getObjUtilities().logReporter("Select year from calendar popup", dateArray[2],
					this.selectDropDownOption(drpYear, dateArray[2], "Value"));
			objPojo.getObjUtilities().logReporter("Select month from calendar popup", dateArray[0],
					this.selectDropDownOption(drpMonth, (dateArray[0].toString()), "Value"));
			objPojo.getObjUtilities().logReporter("Select date from calendar popup", dateArray[1], this.click(lnkDate));
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method : switchToWindowUsingTitle
	 * @Description : This is wrapper method used switch to window using the given
	 *              title
	 * @param :
	 *            locator - Window title
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean switchToWindowUsingTitle(String windowTitle) {
		try {
			String mainWindowHandle = objPojo.getDriver().getWindowHandle();
			Set<String> openWindows = objPojo.getDriver().getWindowHandles();

			if (!openWindows.isEmpty()) {
				for (String windows : openWindows) {
					String window = objPojo.getDriver().switchTo().window(windows).getTitle();
					if (windowTitle.equals(window)) {
						return true;
					}

					if (window.equalsIgnoreCase("New Tab") || window.equals("")) {
						return true;
					} else {
						objPojo.getDriver().switchTo().window(mainWindowHandle);
					}
				}
			}
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method : switchToWindowUsingURL
	 * @Description : This is wrapper method used switch to window using the given
	 *              URL
	 * @param :
	 *            locator - Window title
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean switchToWindowContainsURL(String windowURL) {
		try {
			Set<String> openWindows = objPojo.getDriver().getWindowHandles();
			if (!openWindows.isEmpty()) {
				for (String windows : openWindows) {
					String currentWindowURL = objPojo.getDriver().switchTo().window(windows).getCurrentUrl();
					if (currentWindowURL.contains(windowURL)) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Author : Uday Naik - Valethi Technologies
	 * @Description : Accept Alert Handle Pop up
	 */
	public boolean alertBoxAccept() {
		Alert alert = objPojo.getDriver().switchTo().alert();
		alert.accept();
		return true;
	}

	/**
	 * @Method : selectRadioButton
	 * @Description : This is wrapper method select/deselect radio button
	 * @param :
	 *            locator - By identification of element
	 * @param :
	 *            status - select/deselect
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean selectRadioButton(By locator, boolean status) {
		try {
			waitForElementPresence(locator);
			WebElement webElement = objPojo.getDriver().findElement(locator);
			if (webElement.getAttribute("type").equals("radio")) {
				if ((webElement.isSelected() && !status) || (!webElement.isSelected() && status)) {
					webElement.click();
				}
				if ((webElement.isSelected() && !status) || (!webElement.isSelected() && status)) {
					this.click(locator);
				}
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotSelectableException exception) {
			objPojo.setCustomException("ElementNotSelectableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method : isAlertPresent
	 * @Description : This is wrapper method used for Alert Present or not on Page
	 * @param :
	 *            WebElement - By identification of Locator
	 * @return : - true if Alert is Present
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean isAlertPresent() {
		try {
			if (this.verifyIsAlertPresent()) {
				System.out.println("Alert is present on webpage.");
			} else {
				System.out.println("Alert not present on webpage.");
				return false;
			}
			return true;
		} catch (UnhandledAlertException exception) {
			objPojo.setCustomException("UnhandledAlertException");
			System.out.println("I got no such " + exception.getMessage());
			return false;
		} catch (NoAlertPresentException exception) {
			objPojo.setCustomException("NoAlertPresentException");
			System.out.println("No Alert Present");
			return false;
		} catch (Exception exception) {
			System.out.println(exception.toString());
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method : verifyIsAlertPresent
	 * @Description : This is wrapper method used for Alert Present or not on Page
	 * @param :
	 *            WebElement - By identification of Locator
	 * @return : - true if Alert is Present
	 * @author : Uday Naik - Valethi Technologies
	 */

	public boolean verifyIsAlertPresent() {
		boolean blnFlag = false;
		try {
			objPojo.getDriver().switchTo().alert();
			return blnFlag = true;
		} catch (UnhandledAlertException exception) {
			objPojo.setCustomException("UnhandledAlertException");
			System.out.println("I got no such " + exception.getMessage());
			return false;
		} catch (NoAlertPresentException exception) {
			objPojo.setCustomException("NoAlertPresentException");
			System.out.println("No Alert Present");
			return blnFlag;
		}
	}

	/**
	 * @Method : click
	 * @Description : This is wrapper method to click on web element
	 * @param :
	 *            locator - By identification of element
	 * @return : - true if click successful
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean click(By locator) {
		try {
			waitForElementPresence(locator);
			waitForElementToBeClickable(locator);
			objPojo.getDriver().manage().timeouts().setScriptTimeout(objPojo.getScriptTimeoutWait(), TimeUnit.SECONDS);
			JavascriptExecutor js = (JavascriptExecutor) objPojo.getDriver();
			js.executeScript("return arguments[0].click()", getElementFluent(locator));
			waitAfterEachClick();
			return true;
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (Exception exception) {
			try {
				waitForElementPresence(locator);
				waitForElementToBeClickable(locator);
				getElementFluent(locator).click();
				waitAfterEachClick();
				return true;
			} catch (Exception exceptionJavascript) {
				objPojo.setCustomException("NoSuchElement Exception");
				return false;
			}
		}
	}

	public WebElement getElementFluent(final By locator) throws NoSuchElementException, TimeoutException {
		// System.out.println("**************** into getElementFluent");
		Wait<WebDriver> wait = new FluentWait<>(objPojo.getDriver())

					.withTimeout(Duration.ofSeconds(Integer.parseInt(objPojo.getObjConfig().getProperty("driver.WebDriverWait"))))
			        .pollingEvery(Duration.ofSeconds(Integer.parseInt(objPojo.getObjConfig().getProperty("driver.FluentWaiPulling"))))
			        .ignoring(NoSuchElementException.class);
//				.withTimeout(Integer.parseInt(objPojo.getObjConfig().getProperty("driver.WebDriverWait")),
//						TimeUnit.SECONDS)
//
//
//				.pollingEvery(Integer.parseInt(objPojo.getObjConfig().getProperty("driver.FluentWaiPulling")),
//						TimeUnit.SECONDS)
//				.ignoring(NoSuchElementException.class).ignoring(InvalidElementStateException.class);

		WebElement webElement = wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return objPojo.getDriver().findElement(locator);
			}
		});

		return webElement;
	}

	/**
	 * @Description : This is wrapper method to check the web element is displayed
	 *              on the page
	 * @param :
	 *            locator - By identification of element
	 * @return : - true if element present
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean checkElementDisplayed(By locator) {
		try {
			this.waitForElementVisibilityLocated(locator);
			return objPojo.getDriver().findElement(locator).isDisplayed();
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * @Method : setText
	 * @Description : This is wrapper method to set text for input element
	 * @param :
	 *            locator - By identification of element
	 * @param :
	 *            fieldValue - field value as string
	 * @return : - true if text entered successfully
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean setText(By locator, String fieldValue) {
		try {
			/*
			 * waitForElementPresence(locator); waitForElementVisibilityLocated(locator);
			 */
			WebElement webElement = getElementFluent(locator);
			clearText(webElement);
			// webElement.clear();
			webElement.sendKeys(fieldValue);
			return true;
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
			return false;
		}
	}

	public void clearText(WebElement webElement) {
		/*
		 * Actions navigator = new Actions(objPojo.getDriver());
		 * navigator.click(webElement) .sendKeys(Keys.END) .keyDown(Keys.SHIFT)
		 * .sendKeys(Keys.HOME) .keyUp(Keys.SHIFT) .sendKeys(Keys.BACK_SPACE)
		 * .perform();
		 */
		// webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
		webElement.clear();
	}

	/**
	 * @Method : selectCheckBox
	 * @Description : This is wrapper method select/deselect checkbox
	 * @param :
	 *            locator - By identification of element
	 * @param :
	 *            status - select/deselect
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean selectCheckBox(By locator, boolean status) {
		try {
			waitForElementPresence(locator);
			WebElement webElement = objPojo.getDriver().findElement(locator);
			if (webElement.getAttribute("type").equals("checkbox")) {
				if ((webElement.isSelected() && !status) || (!webElement.isSelected() && status)) {
					webElement.click();
				}
				if ((webElement.isSelected() && !status) || (!webElement.isSelected() && status)) {
					this.click(locator);
				}
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (ElementNotSelectableException exception) {
			objPojo.setCustomException("ElementNotSelectableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		}

		catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			return false;
		}
	}

	/**
	 * @Method : getText
	 * @Description : This is wrapper method to extract the text value of an
	 *              webelement : This method is used for dom ul/li/div/div/h4/span
	 * @param :
	 *            locator - By identification of element
	 * @param :
	 *            textBy - String - "value" or "text"
	 * @return : text value of the passed locator
	 * @Author : Uday Naik - Valethi Technologies
	 *
	 */
	public String getText(By locator, String textBy) {
		String strText = "";
		try {
			waitForElementPresence(locator);
			WebElement webElement = this.objPojo.getDriver().findElement(locator);
			switch (textBy.toLowerCase()) {
			case "value":
				strText = webElement.getAttribute("value");
				break;

			case "text":
				strText = webElement.getText();
				break;
			default:
				strText = webElement.getText();
				break;
			}
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
		} catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
		}
		return strText;
	}

	/**
	 * @Description : This is wrapper method to check the web element is enabled on
	 *              the page
	 * @param :
	 *            locator - By identification of element
	 * @return : - true if element present
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean checkElementEnabled(By locator) {
		try {
			this.waitForElementVisibilityLocated(locator);
			return objPojo.getDriver().findElement(locator).isEnabled();
		} catch (Exception exception) {
			return false;
		}
	}

	public int updateCalculationSheetForSpecificFieldAndGetPackageCost(String ColumnHeader, String valueToUpdate,
			String valueType) {
		String testDataPath = objPojo.getObjConfig().getProperty("testdata.path");
		if (!testDataPath.contains(":")) {
			testDataPath = System.getProperty("user.dir") + testDataPath;
		}
		testDataPath = testDataPath + objPojo.getStrTestDataFilePath() + ".xlsx";
		FileInputStream excelFileIS;
		XSSFWorkbook workbook = null;
		int setterRow = 0;
		int getterRow = 0;
		try {
			excelFileIS = new FileInputStream(testDataPath);
			workbook = new XSSFWorkbook(excelFileIS);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheet("FinalTestData");
		for (int i = 0; i <= sheet.getRow(0).getLastCellNum(); i++) {
			if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(ColumnHeader)) {
				setterRow = i;
			}
			if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase("FinalPriceToVerify")) {
				getterRow = i;
			}
			if (setterRow > 0 && getterRow > 0) {
				break;
			}
		}
		objPojo.getObjUtilities().logReporter("Update Calculation Sheet for Specific Field : " + ColumnHeader,
				valueToUpdate, !valueToUpdate.equals(""));
		if (valueType.equalsIgnoreCase("number")) {
			sheet.getRow(objPojo.getTestDataRow()).createCell(setterRow)
					.setCellValue(Double.parseDouble(valueToUpdate));
		} else if (valueType.equalsIgnoreCase("text")) {
			sheet.getRow(objPojo.getTestDataRow()).createCell(setterRow).setCellValue(valueToUpdate);
		}
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		sheet.getRow(objPojo.getTestDataRow()).getCell(setterRow).setCellStyle(style);
		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
		int packageCost = (int) sheet.getRow(objPojo.getTestDataRow()).getCell(getterRow).getNumericCellValue();
		objPojo.getObjUtilities().logReporter(
				"Package Cost To Be Paid After Updating " + ColumnHeader + " : "
						+ sheet.getRow(objPojo.getTestDataRow()).getCell(getterRow).getNumericCellValue(),
				!valueToUpdate.equals(""));
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(testDataPath);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return packageCost;
	}

	public String executeQueryAndGetResultFromDatabase(String Query, String ColumnHeader) {
		ResultSet rs;
		String id = "";
		try {
			rs = objPojo.getDataBaseStatement().executeQuery(Query);
			while (rs.next()) {
				id = rs.getString(ColumnHeader);
				rs.close();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			objPojo.getObjUtilities().logReporter("Error with Database Connection", true);
			System.exit(0);
		}
		return id;
	}

	/**
	 * @Description : index start with 0 , Hence we have to do "n-1" Also month
	 *              value does not have prefix "0" hence we have to replace "0" with
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean datePickerwithTimeStamp(By locator, String date, String time) {
		try {
			String[] dateArray = date.split("/");
			String month = dateArray[0];
			int tempIntMonth = 0;
			try {
				tempIntMonth = Integer.parseInt(month);
				System.out.println("Current Month is " + tempIntMonth);
				if (tempIntMonth >= 1) {
					if (((dateArray[0].startsWith("0")))) {
						tempIntMonth = tempIntMonth - 1;
						System.out.println("Temp Month is " + tempIntMonth);
						if (tempIntMonth == 0) {
							dateArray[0] = tempIntMonth + "";
						} else {
							dateArray[0] = tempIntMonth + "";
							dateArray[0] = dateArray[0].replaceAll("0", "");
							System.out.println("New Month is " + dateArray[0].toString());
						}
					} else {
						tempIntMonth = tempIntMonth - 1;
						dateArray[0] = tempIntMonth + "";
					}
				} else {
					dateArray[0] = tempIntMonth + "";
					System.out.println("I am in else part " + dateArray[0]);
				}
			} catch (Exception e) {
			}
			By divCalendarPopup = By.xpath("//div[@id='ui-datepicker-div']");
			By lnkDate = By.xpath(
					"//div[@id='ui-datepicker-div']/table[@class='ui-datepicker-calendar']/tbody/tr/td/a[contains(text(),'"
							+ (dateArray[1].startsWith("0") ? dateArray[1].substring(dateArray[1].indexOf("0") + 1)
									: dateArray[1])
							+ "')]");
			By drpMonth = By.xpath(
					"//div[@id='ui-datepicker-div']/div/div[@class='ui-datepicker-title']/select[@class='ui-datepicker-month']");
			By drpYear = By.xpath(
					"//div[@id='ui-datepicker-div']/div/div[@class='ui-datepicker-title']/select[@class='ui-datepicker-year']");

			objPojo.getObjUtilities().logReporter("Click on calendar icon", this.click(locator));
			objPojo.getObjUtilities().logReporter("Verify calendar popup displayed",
					this.checkElementDisplayed(divCalendarPopup));
			objPojo.getObjUtilities().logReporter("Select year from calendar popup", dateArray[2],
					this.selectDropDownOption(drpYear, dateArray[2], "Value"));
			objPojo.getObjUtilities().logReporter("Select month from calendar popup", dateArray[0],
					this.selectDropDownOption(drpMonth, (dateArray[0].toString()), "Value"));
			objPojo.getObjUtilities().logReporter("Select date from calendar popup", dateArray[1], this.click(lnkDate));
			if (!time.equals("")) {
				JavascriptExecutor js = (JavascriptExecutor) objPojo.getDriver();
				js.executeScript("$x('//input[@class='ui_tpicker_time_input']')[0].removeAttribute('disabled')");
				By timestamp = By.xpath("//div[@id='ui-datepicker-div']//input[@class='ui_tpicker_time_input']");
				objPojo.getObjUtilities().logReporter("Set Specific Time in TimeStamp Field Calendar PopUp",
						this.setText(timestamp, time));
			}
			By btnDone = By.xpath("//div[@id='ui-datepicker-div']//button[text()='Done']");
			objPojo.getObjUtilities().logReporter("Click on Calendar PopUp Done Button", this.click(btnDone));
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Description : Use below method to Select Unordered Dropdown Values
	 * @param :
	 *            Locator to click and Get Specific Element Listafter click and
	 *            Value to Select as a text
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public boolean selectUnorderedDropDown(By locator, String value) {
		try {
			this.click(locator);
			this.waitForElementVisibility(objPojo.getDriver().findElement(locator).findElement(By.xpath(
					"..//ancestor::div[@class='selectric']//following-sibling::div[@class='selectric-items']//ul//li")));
			List<WebElement> webElement = objPojo.getDriver().findElement(locator).findElements(By.xpath(
					"..//ancestor::div[@class='selectric']//following-sibling::div[@class='selectric-items']//ul//li"));
			for (WebElement webElement2 : webElement) {
				if (webElement2.getText().equals(value)) {
					click(webElement2);
					return true;
				}
			}
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
		} catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
		}
		return false;
	}

	/**
	 * @Method : sendKeyBoardKeys
	 * @Description : This is wrapper method is used to send keyboard keys
	 * @param :
	 *            locator - By identification of element
	 * @param :
	 *            key - key name
	 * @return : - true if text entered successfully
	 * @author : Uday Naik - Valethi Technologies
	 */
	public boolean sendKeyboardStroke(By locator, String SendKeys) {
		try {
			waitForElementPresence(locator);
			WebElement webElement = objPojo.getDriver().findElement(locator);
			if (SendKeys.equalsIgnoreCase("Tab")) {
				webElement.sendKeys(Keys.TAB);
			} else if (SendKeys.equalsIgnoreCase("Enter")) {
				webElement.sendKeys(Keys.ENTER);
			} else if (SendKeys.equalsIgnoreCase("Down")) {
				webElement.sendKeys(Keys.DOWN);
			} else if (SendKeys.equalsIgnoreCase("Cancel")) {
				webElement.sendKeys(Keys.CANCEL);
			} else if (SendKeys.equalsIgnoreCase("ArrowUp")) {
				webElement.sendKeys(Keys.ARROW_UP);
			} else if (SendKeys.equalsIgnoreCase("Clear")) {
				webElement.sendKeys(Keys.CLEAR);
			} else if (SendKeys.equalsIgnoreCase("ArrowLeft")) {
				webElement.sendKeys(Keys.ARROW_LEFT);
			} else {
				return false;
			}
			return true;
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return false;
		} catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
			return false;
		}

	}

	/**
	 * @Method : getSelectedValueFromDropDown
	 * @Description : This is wrapper method select drop down element
	 * @param :
	 *            locator - By identification of element
	 * @Author : Uday Naik - Valethi Technologies
	 */
	public String getSelectedValueFromDropDown(By locator) {
		try {
			waitForElementPresence(locator);
			Select selectDorpDown = new Select(objPojo.getDriver().findElement(locator));
			String selectedDorpDownValue = selectDorpDown.getFirstSelectedOption().getText();
			return selectedDorpDownValue;
		} catch (NoSuchElementException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got no such " + exception.getMessage());
			exception.printStackTrace();
			return null;
		} catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotSelectableException exception) {
			objPojo.setCustomException("ElementNotSelectableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @Method : Mouse Over Event
	 * @Description : This is wrapper method performing mouse over event
	 * @param :
	 *
	 * @return : - true if mouse over successful
	 * @author : Uday Naik
	 * @created on : 04-01-2023
	 */
	public Boolean mouseOver (By locator) {
		try {
		WebElement ele = objPojo.getDriver().findElement((locator));

		//Creating object of an Actions class
		Actions action = new Actions(objPojo.getDriver());

		//Performing the mouse hover action on the target element.
		action.moveToElement(ele).build().perform();

		//Using java executor
//				JavascriptExecutor js = (JavascriptExecutor) objPojo.getDriver();
//				js.executeScript("arguments[0].mouseOver()", ele);


		return true;
		}
		catch(StaleElementReferenceException ex)
		{
			WebElement ele = objPojo.getDriver().findElement((locator));

			//Creating object of an Actions class
			Actions action = new Actions(objPojo.getDriver());

			//Performing the mouse hover action on the target element.
			action.moveToElement(ele).build().perform();
			return true;
		}

	 catch (TimeoutException exception) {
		objPojo.setCustomException("Timeout & NoSuchElement Exception");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return null;
	} catch (NotFoundException exception) {
		objPojo.setCustomException("NotFound Exception");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return null;
	}

		catch (ElementNotVisibleException exception) {
		objPojo.setCustomException("ElementNotVisibleException");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return null;
	}

		catch (ElementNotInteractableException exception) {
		objPojo.setCustomException("ElementNotInteractableException Exception");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return null;
	}
		catch (ElementNotSelectableException exception) {
		objPojo.setCustomException("ElementNotSelectableException Exception");
		System.out.println("I got timeout " + exception.getMessage());
		exception.printStackTrace();
		return null;
	}

		catch (Exception exception) {
		objPojo.setCustomException("NoSuchElement Exception");
		exception.printStackTrace();
		return null;
	}

	}


	/**
	 * @Method : uploadFileUsingAutoIT
	 * @Description : This is wrapper method to upload file using AutoIT
	 * @param :
	 *            filePath - path of file to upload
	 * @return : - true if upload is successful
	 * @author : Uday Naik
	 * @created on : 03-01-2023
	 */
	// USING AUTO-IT
	public boolean uploadFile(String fileNameOnly, String thinkTime) {
		try {
			/*
			 * String filePath = System.getProperty("user.dir") +
			 * "\\src\\test\\resources\\testData\\AllImageDoc\\" + fileNameOnly;
			 */
			String filePath = fileNameOnly;
			System.out.println("Upload file Path: " + System.getProperty("user.dir")
					+ "\\src\\test\\resources\\testData\\AllImageDoc\\" + fileNameOnly);
			this.waitFor(5);
			String sys_path = System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\";

			Runtime.getRuntime().exec(sys_path + "upload.exe" + " " + filePath);
//			waitFor(thinkTime);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * @Method : Action  Click
	 * @Description : This Method will click on webelement using Action Class
	 * @param : By element
	 *
	 * @return : - true if click on element successfull
	 * @author : Uday Naik
	 * @created on : 04-02-2023
	 */
	public Boolean actionClick (By element) {
		try {
			Actions action = new Actions(objPojo.getDriver());
			WebElement result = objPojo.getDriver().findElement(element);
			action.moveToElement(result).click().perform();

			return true;
		}

		catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotSelectableException exception) {
			objPojo.setCustomException("ElementNotSelectableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
			return null;
		}


	}

	public  String renameReport(String orgPath,String renamePath,String type) {
		Date today = Calendar.getInstance().getTime(); SimpleDateFormat formatter = new
		SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
		File file = new File(orgPath);
		File rename = new File(renamePath+formatter.format(today)+"."+type+" ");
		boolean flag = file.renameTo(rename);
			if (flag) {
        System.out.println("File Successfully Rename");
    }
				else {
        System.out.println("Operation Failed");
    }
			return rename.toString();

	}
	/**
	 * @Method : OpenNewWindow
	 * @Description : This method help us to open new tab.
	 * @param :
	 *
	 * @return : -
	 * @author : Dhanshree Swami
	 * @created on : 15-02-2023
	 */

	public void switchWindow(String url) {

		((JavascriptExecutor)objPojo.getDriver()).executeScript("window.open()");
		ArrayList<String> tabs=new ArrayList<>(objPojo.getDriver().getWindowHandles());
		objPojo.getDriver().switchTo().window(tabs.get(2));
		objPojo.getDriver().get(url);
		}


	/**
	 * @Method : SwitchToBackendWindow
	 * @Description : This method help us to switch new window.
	 * @param :
	 *
	 * @return : -
	 * @author : Ratnadeep Kawale
	 * @created on : 30-05-2023
	 */
	public void SwitchToBackendWindow() {
		objPojo.getObjWrapperFunctions().waitFor(5);
		Set<String>id=objPojo.getDriver().getWindowHandles();
	      Iterator<String> ids=id.iterator();
	      String Parent=ids.next();
	      String Child1=ids.next();
		  objPojo.getDriver().switchTo().window(Child1);
	}

	/**
	 * @Method : tooltipText
	 * @Description : Reading ToolTip text in Selenium-WebDriver
	 * @param : locator
	 *
	 * @return : - tooltip
	 * @author : Ratnadeep Kawale
	 * @created on : 27-06-2023
	 */

	public String tooltipText(By locator){
		String tooltip = objPojo.getDriver().findElement(locator).getAttribute("title");
		return tooltip;
		}

	/**
	 * @Method : OpenNewWindow
	 * @Description : This method help us to open new tab.
	 * @param :
	 *
	 * @return : -
	 * @author : Dhanshree Swami
	 * @created on : 15-02-2023
	 */

	public void switchWindowDynamically(String url,int index) {

		((JavascriptExecutor)objPojo.getDriver()).executeScript("window.open()");
		ArrayList<String> tabs=new ArrayList<>(objPojo.getDriver().getWindowHandles());
		objPojo.getDriver().switchTo().window(tabs.get(index));
		objPojo.getDriver().get(url);
		}

	/**
	 * @Method: writeArticleNumber
	 * @Description :This method is used to write the data from text file
	 * @param :articleFilePath,articleID
	 *
	 * @return:
	 * @author : Uday Naik
	 * @created on : 26-07-2023
	 */

	public  void writeArticleNumber(String articleFilePath,String articleID) {

        try {
            Path path = Paths.get(articleFilePath);
            Files.write(path, articleID.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * @Method: readArticleNumberId
	 * @Description :This method is used to read the data from text file
	 * @param :articleFilePath
	 *
	 * @return:
	 * @author : Uday Naik
	 * @created on : 26-07-2023
	 */

	public static String readArticleNumberId(String articleFilePath) {
		File file = new File(articleFilePath);
        Scanner sc = null;
        String articleNumber="";
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        while (sc.hasNextLine()) {
			articleNumber= (sc.nextLine());
		}
		return articleNumber;

    }

	/**
	 * @Method: actionMouseClick
	 * @Description :This Method while Perform The mouse Click on Specified Element.
	 * @param :
	 *
	 * @return:
	 * @author : Dhanshree Swami
	 * @created on : 10-07-2023
	 */
	public Boolean actionMouseClick (By element) {
		try {
			waitForElementPresence(element);
			Actions action = new Actions(objPojo.getDriver());
			WebElement result = objPojo.getDriver().findElement(element);
			action.click(result).build().perform();

			return true;
		}

		catch (TimeoutException exception) {
			objPojo.setCustomException("Timeout & NoSuchElement Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		} catch (NotFoundException exception) {
			objPojo.setCustomException("NotFound Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotVisibleException exception) {
			objPojo.setCustomException("ElementNotVisibleException");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotInteractableException exception) {
			objPojo.setCustomException("ElementNotInteractableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (ElementNotSelectableException exception) {
			objPojo.setCustomException("ElementNotSelectableException Exception");
			System.out.println("I got timeout " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}

		catch (Exception exception) {
			objPojo.setCustomException("NoSuchElement Exception");
			exception.printStackTrace();
			return null;
		}

	}

	/**
	 * @Method: validatePagination
	 * @Description :This method is used to validate the pagination of data table
	 * @param : firstColumnListLocator - Data Table Column listing - To get the size
	 *          of the total entries
	 * @param : nextBtnLocator - By identification of element - Next button locator
	 * @param : showEntryLocator - By identification of element
	 * @param : showEntry - drop down element (user may specify text/value/index)
	 * @param : selectType - select dorp down element by Text/Value/Index
	 * @author : Harvinder Singh
	 * @return : String - Pagination
	 * @created on : 29-11-2023
	 */
	public String validatePagination(By firstColumnListLocator, By nextBtnLocator, By showEntryLocator,
			String showEntry, String selectType) {
		try {
			selectDropDownOption(showEntryLocator, showEntry, selectType);
			List<WebElement> dTableColListWebElement = objPojo.getDriver().findElements(firstColumnListLocator);
			List<String> dTableColList = new ArrayList<>();
			for (WebElement dTableColElement : dTableColListWebElement) {
				dTableColList.add(dTableColElement.getText());
			}
			String getNextBtnCls = objPojo.getDriver().findElement(nextBtnLocator).getAttribute("class");
			while (!getNextBtnCls.contains("disabled")) {
				scrollToViewDown(nextBtnLocator);
				elementHighlight(nextBtnLocator);
				objPojo.getObjWrapperFunctions().click(nextBtnLocator);
				objPojo.getObjWrapperFunctions().waitFor(1);
				dTableColListWebElement = objPojo.getDriver().findElements(firstColumnListLocator);
				for (WebElement dTableColElement : dTableColListWebElement) {
					dTableColList.add(dTableColElement.getText());
				}
				getNextBtnCls = objPojo.getDriver().findElement(nextBtnLocator).getAttribute("class");
			}
			int actualCount = dTableColList.size();
			if (showEntry.equalsIgnoreCase("All")) {
				return "Showing 1 to " + actualCount + " of " + actualCount + " entries";
			} else {
				int quotient = actualCount / Integer.parseInt(showEntry);
				int pagStart = (Integer.parseInt(showEntry) * quotient) + 1;
				return "Showing " + pagStart + " to " + actualCount + " of " + actualCount + " entries";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Pagination does not matched";
		}
	}

	/**
	 * @Method: doubleClickElement
	 * @Description :This Method while Perform The doubleClick on Specified Element.
	 * @param :
	 *
	 * @return:
	 * @author : Ratnadip Kawale
	 * @created on : 11-02-2025
	 */

	 public boolean doubleClickElement(By element) {
	        try {
	        	WebElement elementClick= objPojo.getDriver().findElement(element);
	            Actions actions = new Actions(objPojo.getDriver());
	            actions.doubleClick(elementClick).perform();
	            System.out.println("Double click performed on the element.");
	            return true;
	        } catch (Exception e) {
	            System.out.println("Error while performing double click: " + e.getMessage());
	            return false;
	        }
	    }

	 /**
		 * @Method: extractProjectNameWithoutId
		 * @Description :This Method is used to extract the project Name from projectId
		 * @param :
		 *
		 * @return:
		 * @author : Nikhil Magar
		 * @created on : 16-07-2025
		 */

public String extractProjectNameWithoutId(String input){
		String result=  input.split("-")[0].trim();
		return result;
	}

public String getCurrentUrl() {
    return objPojo.getDriver().getCurrentUrl();
}
}


