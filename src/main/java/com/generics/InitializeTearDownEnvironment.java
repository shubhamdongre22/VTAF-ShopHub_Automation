package com.generics;

import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InitializeTearDownEnvironment {
	private WebDriver webDriver;

	@SuppressWarnings("deprecation")
	public WebDriver initializeWebEnvironment(Properties objConfig) {
		boolean execRemote = objConfig.getProperty("ExecutionMode").trim().equalsIgnoreCase("remote");
		try {
			String browser = objConfig.getProperty("web.browser").trim().toLowerCase();


			if (browser.equalsIgnoreCase("chrome")) {
				if (execRemote) {
					DesiredCapabilities caps = new DesiredCapabilities();
					caps.setCapability(CapabilityType.BROWSER_NAME,"chrome");
					this.webDriver = new RemoteWebDriver(new URL("http://192.168.1.8:4444/wd/hub"),caps);

					//RemoteWebDriver this.webDriver = new ChromeDriver();
				}
//				else {
//					System.setProperty("webdriver.http.factory", "jdk-http-client");
//					WebDriverManager.chromedriver().driverVersion(objConfig.getProperty("chromeVersion")).setup();
//
//					System.setProperty("webdriver.chrome.driver",
//							System.getProperty("user.dir") + objConfig.getProperty("webdriver.chrome.driver").trim());
				else {
				    System.setProperty("webdriver.http.factory", "jdk-http-client");

				    WebDriverManager.chromedriver().setup();

				    this.webDriver = new ChromeDriver();
				}
//					ChromeOptions obj = new ChromeOptions();
//					if (objConfig.getProperty("chromeIncognito").equalsIgnoreCase("true"))
//						obj.addArguments("incognito");
//					obj.setPageLoadStrategy(PageLoadStrategy.NONE);
//					if (objConfig.getProperty("headless").equalsIgnoreCase("true"))
//						obj.addArguments("--headless");
					this.webDriver = new ChromeDriver();
				}
			 else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + objConfig.getProperty("webdriver.firefox.driver").trim());
				if (execRemote) {
					File pathBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
					FirefoxOptions option = new FirefoxOptions();

					FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);

					DesiredCapabilities caps = new DesiredCapabilities();

					caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, option.setBinary(firefoxBinary));

					caps.setCapability(CapabilityType.BROWSER_NAME,"firefox");

					this.webDriver = new RemoteWebDriver(new URL("http://172.16.1.124:4444/wd/hub"),caps);



				} else {
					WebDriverManager.firefoxdriver().setup();

					this.webDriver = new FirefoxDriver();
				}
			} else if (browser.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", objConfig.getProperty("webdriver.ie.driver").trim());
				this.webDriver = new InternetExplorerDriver();
			}

			// ############################################Edge Browser

			else if (browser.equalsIgnoreCase("edge")) {
				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + objConfig.getProperty("webdriver.edge.driver").trim());
				if (execRemote) {

//					File pathBinary = new File("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
//					EdgeOptions option = new EdgeOptions();

					DesiredCapabilities caps = new DesiredCapabilities();


					//caps.setCapability(EdgeOptions.CAPABILITY, option.setBinary(pathBinary));
					caps.setCapability(CapabilityType.BROWSER_NAME,"edge");

					EdgeOptions options = new EdgeOptions();
					options.setCapability("useAutomationExtension", false);
					//URL hubUrl = new URL("http://wiqaseleniumhub.fmr.com:4444/wd/hub");
					//WebDriver driver = new RemoteWebDriver(hubUrl, options);

					this.webDriver = new RemoteWebDriver(new URL("http://172.16.1.124:4444/wd/hub"),options);

					//this.webDriver = new RemoteWebDriver(DesiredCapabilities.edge());
				} else {
					System.setProperty("webdriver.http.factory", "jdk-http-client");
					WebDriverManager.edgedriver().setup();
					this.webDriver = new EdgeDriver();
				}

			}

			// #############################################Edge Browser


			else if (browser.equalsIgnoreCase("safari")) {

				if (execRemote) {

					DesiredCapabilities caps = new DesiredCapabilities();
					caps.setCapability(CapabilityType.BROWSER_NAME,"safari");
					this.webDriver = new RemoteWebDriver(new URL(""),caps);
					//this.webDriver = new RemoteWebDriver(DesiredCapabilities.safari());
				} else {
					WebDriverManager.edgedriver().setup();
					this.webDriver = new SafariDriver();
				}

			}

		//##############Browser Stack##############################

			else if (browser.equalsIgnoreCase("browserStack")) {

				final String userName=objConfig.getProperty("userName");
				final String automateKey=objConfig.getProperty("automateKey");
				final String url="https://" + userName + ":"+automateKey+"@hub-cloud.browserstack.com/wd/hub" ;

				DesiredCapabilities cap = new DesiredCapabilities();
				/*
				 * cap.setCapability("platformName", "ios");
				 * cap.setCapability("platformVersion", "16"); cap.setCapability("deviceName",
				 * "iPhone 14"); //cap.setCapability(browser, execRemote);
				 *
				 * cap.setCapability("resolution", "768x768"); cap.setCapability("buildName",
				 * "Java Screen Resolution test Sample Build"); cap.setCapability("sessionName",
				 * "Screen Resolution test"); // Set the selenium version.
				 * cap.setCapability("seleniumVersion", "3.141.59");
				 */

				cap.setCapability("os",objConfig.getProperty("os"));
				cap.setCapability("os_version",objConfig.getProperty("os_version"));
				cap.setCapability("browser",objConfig.getProperty("browser"));
				cap.setCapability("browser_version",objConfig.getProperty("browser_version"));
				cap.setCapability("name",objConfig.getProperty("name"));
				//cap.setCapability("os","Windows");


				this.webDriver = new RemoteWebDriver(new URL(url),cap);


			}

		//##############Browser Stack##############################



			this.webDriver.manage().window().maximize();
			this.webDriver.manage().timeouts().implicitlyWait(
					Integer.parseInt(objConfig.getProperty("driver.implicitlyWait").trim()), TimeUnit.SECONDS);
			this.webDriver.manage().timeouts().pageLoadTimeout(
					Integer.parseInt(objConfig.getProperty("driver.pageLoadTimeout").trim()), TimeUnit.SECONDS);
			return this.webDriver;
		}

		catch (Exception var4) {
			var4.printStackTrace();
			return null;
		}
	}

	public void tearDownWebEnvironment(Properties objConfig) {
		try {
			this.webDriver.quit();
		} catch (Exception var3) {
			var3.printStackTrace();
			if (System.getProperty("web.browser").trim().equalsIgnoreCase("IE")
					|| System.getProperty("web.browser").trim().equalsIgnoreCase("Chrome")
					|| System.getProperty("web.browser").trim().equalsIgnoreCase("edge")
					|| System.getProperty("web.browser").trim().equalsIgnoreCase("firefox")) {
				this.killBrowserAndDriver(objConfig);
			}
		}

	}

	protected void killBrowserAndDriver(Properties objConfig) {
		String browser = objConfig.getProperty("web.browser").trim();
		String browserProcess = "";
		String driverProcess = "";
		if (!browser.equals("") && browser.equalsIgnoreCase("IE")) {
			browserProcess = "iexplore";
			driverProcess = "IEDriverServer.exe";
		} else if (!browser.equals("") && browser.equalsIgnoreCase("Chrome")) {
			browserProcess = browser;
			driverProcess = "chromedriver.exe";
		}

		else if (!browser.equals("") && browser.equalsIgnoreCase("firfox")) {
			browserProcess = browser;
			driverProcess = "geckodriver.exe";
		}

		else if (!browser.equals("") && browser.equalsIgnoreCase("edge")) {
			browserProcess = browser;
			driverProcess = "msedgedriver.exe";
		}

		try {
			Process procDriver = Runtime.getRuntime().exec("taskkill /F /T /IM " + driverProcess);
			procDriver.waitFor();
		} catch (Exception var7) {
			var7.printStackTrace();
		}

	}
}