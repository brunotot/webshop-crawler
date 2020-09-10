package com.crawler.util;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crawler.script.Scripts;

public class Helper {
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String openInNewTab(WebDriver driver, String url) {
		Set<String> winHandles = driver.getWindowHandles();
		int totalHandles = winHandles.size();
		((JavascriptExecutor) driver).executeScript(Scripts.SCRIPT_WINDOW_OPEN);
		new WebDriverWait(driver, 5).until(ExpectedConditions.numberOfWindowsToBe(totalHandles + 1));
		driver.navigate().to(url);
		return driver.getWindowHandle();
	}
	
	public static String openInNewTab(WebDriver driver, WebElement element) {
		Set<String> winHandles = driver.getWindowHandles();
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN); 
		element.sendKeys(selectLinkOpeninNewTab);
		new WebDriverWait(driver, 5).until(ExpectedConditions.numberOfWindowsToBe(winHandles.size() + 1));
		Set<String> newWinHandles = driver.getWindowHandles();
		String h = null;
		for (String newHandle : newWinHandles) {
			boolean found = true;
			for (String oldHandle : winHandles) {
				if (oldHandle.equals(newHandle)) {
					found = false;
					break;
				}
			}
			if (found) {
				h = newHandle;
				driver.switchTo().window(newHandle);
				break;
			}
		}
		return h;
	}
	
	public static WebElement findElement(WebDriver driver, By... bies) {
		for (By by : bies) {
			try {
				WebElement webElement = driver.findElement(by);
				return webElement;
			} catch (Exception e) {}
		}
		return null;
	}

	public static WebElement findElement(WebElement element, By... bies) {
		for (By by : bies) {
			try {
				WebElement webElement = element.findElement(by);
				return webElement;
			} catch (Exception e) {}
		}
		return null;
	}
	
}
