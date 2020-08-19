package com.crawler.shops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.crawler.enums.Category;
import com.crawler.interfaces.WebShop;
import com.crawler.model.Item;

public class Kaufland implements WebShop {

	private Map<String, Category> kategorijeUrls;
	
	private List<Item> items;

	private WebDriver driver;
	
	public Kaufland() throws Exception {
		this.items = new ArrayList<>();
		this.kategorijeUrls = new HashMap<>();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		this.driver = new ChromeDriver(options);
		this.driver.navigate().to("https://www.kaufland.hr/ponuda/ponuda-pregled.html");
		Thread.sleep(2000);
		this.driver.findElement(By.className("cookie-alert-extended-button")).click();
		Thread.sleep(500);
		WebElement kategorijeWrapper = this.driver.findElement(By.xpath("//*[@id=\"offers-overview-1\"]/ul"));
		List<WebElement> kategorije = kategorijeWrapper.findElements(By.cssSelector("li > a"));
		for (WebElement kategorija : kategorije) {
			this.kategorijeUrls.put(kategorija.getAttribute("href"), Category.get(kategorija.getText()));
		}
	}

	@Override
	public List<Item> getItemsFromCategory(Category category) {
		List<Item> itemsFromCategory = new ArrayList<>();
		this.items.forEach(item -> {
			if (item.getCategory().equals(category)) {
				itemsFromCategory.add(item);
			}
		});
		return itemsFromCategory;
	}

	@Override
	public List<Item> getItems() {
		return this.items;
	}

	@Override
	public void updateItemsList() throws Exception {
		for (Map.Entry<String, Category> entry : this.kategorijeUrls.entrySet()) {
			String url = entry.getKey();
			Category category = entry.getValue();
			this.driver.navigate().to(url);
			Thread.sleep(3000);
		}
	}

	@Override
	public void close() {
		this.driver.close();
	}
	
	@Override
	public Map<String, Category> getKategorijeUrls() {
		return this.kategorijeUrls;
	}
	
}
