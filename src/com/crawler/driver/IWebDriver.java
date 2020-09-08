package com.crawler.driver;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IWebDriver implements WebDriver {
	
	public static final String SCRIPT_SCROLL_INTO_VIEW = "arguments[0].scrollIntoView({behavior:'auto',block:'center',inline:'center'});";

	private WebDriver driver;
	
	public IWebDriver() {
		reloadDriver();
	}
	
	public void openInNewTab(String url) {
		if (driver == null) {
			reloadDriver();
			driver.navigate().to(url);
		} else {
			Set<String> winHandles = driver.getWindowHandles();
			int totalHandles = winHandles.size();
			if (totalHandles == 0) {
				driver.navigate().to(url);
			} else {
				((JavascriptExecutor) driver).executeScript("window.open()");
				new WebDriverWait(driver, 5).until(ExpectedConditions.numberOfWindowsToBe(totalHandles + 1));
				driver.navigate().to(url);
			}
		}
	}
	
	public void openInNewTab(WebElement element) {
		if (driver == null) {
			return;
		}
		Set<String> winHandles = driver.getWindowHandles();
		if (winHandles.size() == 0) {
			return;
		}
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN); 
		element.sendKeys(selectLinkOpeninNewTab);
		new WebDriverWait(driver, 5).until(ExpectedConditions.numberOfWindowsToBe(winHandles.size() + 1));
		Set<String> newWinHandles = driver.getWindowHandles();
		for (String newHandle : newWinHandles) {
			boolean found = true;
			for (String oldHandle : winHandles) {
				if (oldHandle.equals(newHandle)) {
					found = false;
					break;
				}
			}
			if (found) {
				driver.switchTo().window(newHandle);
				break;
			}
		}
	}
	
	private void reloadDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		//options.addArguments("--proxy-server=http://" + "83.97.23.90:18080");
		this.driver = new ChromeDriver(options);
	}
	
	@Override
	public void get(String url) {
		this.driver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		return this.driver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		return this.driver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return this.driver.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return this.driver.findElement(by);
	}

	@Override
	public String getPageSource() {
		return this.driver.getPageSource();
	}

	@Override
	public void close() {
		this.driver.close();
	}

	@Override
	public void quit() {
		this.driver.quit();
	}

	@Override
	public Set<String> getWindowHandles() {
		return this.driver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		return this.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		return this.driver.switchTo();
	}

	@Override
	public Navigation navigate() {
		return this.driver.navigate();
	}

	@Override
	public Options manage() {
		return this.driver.manage();
	}

}
