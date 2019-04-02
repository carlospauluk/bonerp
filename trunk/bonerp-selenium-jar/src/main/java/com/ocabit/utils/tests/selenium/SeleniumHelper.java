package com.ocabit.utils.tests.selenium;



import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Classe com métodos utilitários para testes com Selenium.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public class SeleniumHelper {
	protected static Logger logger = Logger.getLogger(SeleniumHelper.class);
	
	private WebDriver driver;

	private static int timeToWait = 4;

	public SeleniumHelper(Browser browser) {
		System.setProperty("webdriver.chrome.driver", "D:\\ocabit\\bonerp\\trunk\\bonerp-selenium-jar\\src\\main\\resources\\chromedriver-2.21.exe");
		System.setProperty("webdriver.ie.driver", "D:\\ocabit\\bonerp\\trunk\\bonerp-selenium-jar\\src\\main\\resources\\IEDriverServer-x64.exe");
		switch (browser) {
			case HTMLUNIT:
				setDriver(new HtmlUnitDriver(true));
				break;
			case IE:
				setDriver(new InternetExplorerDriver());
				break;
			case FIREFOX:
				setDriver(new FirefoxDriver());
				break;
			case REMOTE:
				try {
					DesiredCapabilities capability = DesiredCapabilities.htmlUnitWithJs();
					//DesiredCapabilities capability = DesiredCapabilities.firefox();
					//capability.setBrowserName("firefox"); 
					//capability.setBrowserName("htmlunit"); 
					//capability.setVersion("47.0.1"); 
					//capability.setPlatform(Platform.WINDOWS);  
					setDriver(new RemoteWebDriver(new URL("http://10.1.1.235:4444/wd/hub"), capability));
					break;	
				} catch (MalformedURLException e) {
					throw new IllegalStateException(e);
				}
			case CHROME:
				ChromeOptions options = new ChromeOptions();
				options.setBinary("D:\\Chrome\\chrome32_48.0.2564.97\\Chrome-48.0.2564.97\\chrome.exe");
				//options.setBinary("D:\\Chrome\\x64_48.0.2564.109\\chrome.exe");
				//options.setBinary("D:\\Chrome\\chrome32_51.0.2704.84\\Chrome32_51.0.2704.84\\chrome.exe");
				setDriver(new ChromeDriver(options));
				break;
		}
	}

	public SeleniumHelper(WebDriver driver) {
		setDriver(driver);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public boolean textPresent(String text) {
		boolean ok = false;
		int t = 30;
		for (int i=0;i<t;i++) {
			try {
				ok = driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*"+text+"[\\s\\S]*$");
				Thread.sleep(100);
				break;
			}
			catch (StaleElementReferenceException e) {
				logger.info("Tentando encontrar o texto '"+text+ "' i="+i);
			}
			catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}
		return ok;
	}
	
	public String textById(String selector) {
		return byId(selector).getText();
	}
	
	public String textByIdEnding(String selector) {
		return byIdEnding(selector).getText();
	}
	
	
	// FIND ELEMENT BY...

	public WebElement byCSS(String selector) {
		return (new WebDriverWait(getDriver(), timeToWait))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
	}

	public WebElement byId(String selector) {
		return (new WebDriverWait(getDriver(), timeToWait))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(selector)));
	}
	
	public WebElement byIdEnding(String selector) {
		return (new WebDriverWait(getDriver(), timeToWait))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id*='"+selector+"']")));
	}

	public WebElement byXPath(String selector) {
		return (new WebDriverWait(getDriver(), timeToWait))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selector)));
	}

	public int qtdById(String selector) {
		return driver.findElements(By.id(selector)).size();
	}
	
	public int qtdByIdEnding(String selector) {
		return driver.findElements(By.cssSelector("[id*='"+selector+"']")).size();
	}
	
	// GET ATTRIBUTE BY...

	public String attribByCSS(String selector, String attribName) {
		return byCSS(selector).getAttribute(attribName);
	}

	public String attribById(String selector, String attribName) {
		return byId(selector).getAttribute(attribName);
	}

	public String attribByXPath(String selector, String attribName) {
		return byXPath(selector).getAttribute(attribName);
	}

	// GET VALUE BY...

	public String valueByCSS(String selector) {
		return attribByCSS(selector, "value");
	}

	public String valueById(String selector) {
		return attribById(selector, "value");
	}
	
	public String valueByIdEnding(String selector) {
		return attribByCSS("[id*='"+selector+"']", "value");
	}

	public String valueByXPath(String selector) {
		return attribByXPath(selector, "value");
	}
	
	public void clickById(String selector) {
		boolean ok = false;
		int t = 30;
		for (int i=0;i<t;i++) {
			try {
				this.byId(selector).click();
				ok = true;
				break;
			}
			catch (WebDriverException e) {
				logger.info(e.getMessage());
				logger.info("Tentando clicar em "+selector+ " i="+i);
			}
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}
		if (! ok) {
			throw new IllegalStateException("clickById não achou elemento ["+selector+"]"); 
		}
	}
	
	public void clickByIdEnding(String selector) {
		boolean ok = false;
		int t = 30;
		for (int i=0;i<t;i++) {
			try {
				this.byIdEnding(selector).click();
				ok = true;
				break;
			}
			catch (WebDriverException e) {
				logger.info(e.getMessage());
				logger.info("Tentando clicar em "+selector+ " i="+i);
			}
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}
		if (! ok) {
			throw new IllegalStateException("clickByIdEnding não achou elemento ["+selector+"]"); 
		}	
	}

	public enum Browser {
		HTMLUNIT,
		CHROME,
		FIREFOX,
		IE,
		REMOTE;
	}

}
