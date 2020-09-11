package com.crawler.driver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crawler.script.Scripts;

import net.sf.T0rlib4j.controller.network.JavaTorRelay;

public class IWebDriver implements WebDriver {

	private JavaTorRelay node;
	
	private WebDriver driver;
	
	private boolean enableTorProxy;
	
	private boolean enableJavaScript;
	
	private ChromeOptions options;
	
	public IWebDriver() throws Exception {
		this.enableJavaScript = true;
		this.enableTorProxy = false;
		setEnableTorProxy(true);
		this.options = new ChromeOptions();
		this.options.addArguments("--proxy-server=socks5://127.0.0.1:" + this.node.getLocalPort());
		initDriver();
	}

	public void setEnableTorProxy(boolean enable) throws Exception {
		if (enable == this.enableTorProxy) {
			return;
		}
		if (enable) {
			this.node = new JavaTorRelay(new File("torfiles"));
			this.enableTorProxy = true;
		} else {
			if (this.node != null) {
				this.node.ShutDown();
				this.node = null;
			}
			this.enableTorProxy = false;
		}
	}
	
	public void setEnableJavaScript(boolean enable) throws Exception {
		if (this.driver == null) {
			throw new Exception("Driver is NULL");
		}
		if (this.enableJavaScript == enable) {
			return;
		}
		this.enableJavaScript = enable;
		String currentUrl = getCurrentUrl();
		if (enable) {
	        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	        chromePrefs.put("profile.default_content_setting_values.javascript", 2);
	        this.options.setExperimentalOption("prefs", chromePrefs);
		} else {
	        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	        chromePrefs.put("profile.default_content_setting_values.javascript", 1);
	        this.options.setExperimentalOption("prefs", chromePrefs);
		}
		if (this.enableTorProxy) {
    		this.options.addArguments("--proxy-server=socks5://127.0.0.1:" + this.node.getLocalPort());
        }
		initDriver();
		if (currentUrl != null && !currentUrl.isEmpty()) {
			driver.navigate().to(currentUrl);
		}
	}
	
	public JavascriptExecutor js() {
		return (JavascriptExecutor) this.driver;
	}
	
	public void rotateIp() throws Exception {
		if (this.node != null) {
			this.node.ShutDown();
			this.node = null;
		}
		this.enableTorProxy = false;
		setEnableTorProxy(true);
		this.options = new ChromeOptions();
		this.options.addArguments("--proxy-server=socks5://127.0.0.1:" + this.node.getLocalPort());
		initDriver();
	}
	
	public boolean isEnableTorProxy() {
		return this.enableTorProxy;
	}
	
	public boolean isEnableJavaScript() {
		return this.enableJavaScript;
	}
	
	private void initDriver() throws Exception {
		if (this.driver != null) {
			this.driver.quit();
			this.driver = null;
		}
		this.driver = new ChromeDriver(this.options);
		this.driver.manage().window().setSize(new Dimension(1920, 1080));
		setEnableJavaScript(true);
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
		return this.driver.getWindowHandle();
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

	public String openInNewTab(String url) {
		Set<String> winHandles = this.driver.getWindowHandles();
		int totalHandles = winHandles.size();
		((JavascriptExecutor) driver).executeScript(Scripts.SCRIPT_WINDOW_OPEN);
		new WebDriverWait(driver, 5).until(ExpectedConditions.numberOfWindowsToBe(totalHandles + 1));
		this.driver.navigate().to(url);
		return this.driver.getWindowHandle();
	}
	
	public String openInNewTab(WebElement element) {
		Set<String> winHandles = this.driver.getWindowHandles();
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN); 
		element.sendKeys(selectLinkOpeninNewTab);
		new WebDriverWait(this.driver, 5).until(ExpectedConditions.numberOfWindowsToBe(winHandles.size() + 1));
		Set<String> newWinHandles = this.driver.getWindowHandles();
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
				this.driver.switchTo().window(newHandle);
				break;
			}
		}
		return h;
	}
	
	public WebElement findElement(By... bies) {
		for (By by : bies) {
			try {
				WebElement webElement = driver.findElement(by);
				return webElement;
			} catch (Exception e) {}
		}
		return null;
	}

	public WebElement findElement(WebElement element, By... bies) {
		for (By by : bies) {
			try {
				WebElement webElement = element.findElement(by);
				return webElement;
			} catch (Exception e) {}
		}
		return null;
	}
	
}
