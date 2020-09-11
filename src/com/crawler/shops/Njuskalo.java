package com.crawler.shops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.crawler.driver.IWebDriver;
import com.crawler.enums.Category;
import com.crawler.interfaces.WebShop;
import com.crawler.model.Item;
import com.crawler.util.Helper;

public class Njuskalo implements WebShop {

	private Map<String, Category> kategorijeUrls;
	
	private List<Item> items;

	private IWebDriver driver;
	
	public Njuskalo() throws Exception {
		this.items = new ArrayList<>();
		this.kategorijeUrls = new HashMap<>();
		this.driver = new IWebDriver();
		this.driver.setEnableJavaScript(false);
		this.driver.navigate().to(getBaseUrl());
		Helper.sleep(20000);
		WebElement highlighted = this.driver.findElement(By.xpath("/html/body/div[9]/div[3]/div[1]/main/div/div[1]/nav/ul[1]"));
		WebElement normal = this.driver.findElement(By.xpath("/html/body/div[9]/div[3]/div[1]/main/div/div[1]/nav/ul[2]"));
		List<WebElement> highlightedElements = highlighted.findElements(By.cssSelector("li a"));
		for (WebElement elem : highlightedElements) {
			String href = elem.getAttribute("href");
			String categoryName = elem.getText().split("\n")[0];
			Category cat = Category.get(categoryName);
			if (cat != null) {
				this.kategorijeUrls.put(href, cat);
			}
		}
		List<WebElement> normalElements = normal.findElements(By.cssSelector("li a"));
		for (WebElement elem : normalElements) {
			String href = elem.getAttribute("href");
			String categoryName = elem.getText().split("\n")[0];
			Category cat = Category.get(categoryName);
			if (cat != null) {
				this.kategorijeUrls.put(href, cat);
			}
		}
		this.kategorijeUrls.forEach((category, href) -> {
			System.out.println(category + " : " + href);
		});
		updateItems();
	}
	
	@Override
	public String getBaseUrl() {
		return "https://www.njuskalo.hr/";
	}

	@Override
	public String getShopImageUrl() {
		return "https://aircash.eu/updocsi17/njuskalo.jpg";
	}

	@Override
	public List<Item> getItems(Category category) {
		return this.items
				.stream()
				.filter(item -> item.getCategory().equals(category))
				.collect(Collectors.toList());
	}

	@Override
	public List<Item> getItems() {
		return this.items;
	}

	@Override
	public void updateItems() throws Exception {
		
	}

	@Override
	public void close() {
		this.driver.close();
	}

	@Override
	public JavascriptExecutor js() {
		return this.driver.js();
	}

}
