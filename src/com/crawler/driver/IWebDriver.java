package com.crawler.driver;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import net.sf.T0rlib4j.controller.network.JavaTorRelay;

public class IWebDriver implements WebDriver {

	private JavaTorRelay node;
	
	private WebDriver driver;
	
	private Integer torProxyPort;
	
	private String torProxyIp;
	
	private String protocol;
	
	public IWebDriver(boolean includeTorProxy) throws Exception {
		if (includeTorProxy) {
			initializeTorProxy(null);
		} else {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			this.driver = new ChromeDriver(options);
		}
	}

	public void rotateIp() throws Exception {
		initializeTorProxy(getCurrentUrl());
	}
	
	public void stopTor() throws Exception {
		if (isTorRunning()) {
			this.node.ShutDown();
			this.node = null;
		}
	}
	
	public boolean isTorRunning() {
		return this.node != null;
	}
	
	private void initializeTorProxy(String url) throws Exception {
		if (this.node != null) {
			this.node.ShutDown();
			this.node = null;
		}
		if (this.driver != null) {
			this.driver.quit();
			this.driver = null;
		}
		this.node = new JavaTorRelay(new File("torfiles"));
        this.torProxyPort = node.getLocalPort();
        this.torProxyIp = "127.0.0.1";
        this.protocol = "socks5";
        ChromeOptions options = new ChromeOptions();
        String proxy = this.protocol + "://" + this.torProxyIp + ":" + this.torProxyPort;
		options.addArguments("--proxy-server=" + proxy);
		options.addArguments("start-maximized");
//		options.addArguments("--headless");
//		options.addArguments("window-size=1920,1080");
		this.driver = new ChromeDriver(options);
		if (url != null && !url.isEmpty()) {
			this.driver.navigate().to(url);
		}
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

}
