package com.crawler.shops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crawler.driver.IWebDriver;
import com.crawler.enums.Category;
import com.crawler.interfaces.WebShop;
import com.crawler.model.Item;
import com.crawler.script.Scripts;
import com.crawler.util.Helper;

public class Kaufland implements WebShop {
	
	private Map<String, Category> kategorijeUrls;
	
	private List<Item> items;

	private IWebDriver driver;
		
	public Kaufland() throws Exception {
		this.items = new ArrayList<>();
		this.kategorijeUrls = new HashMap<>();
		this.driver = new IWebDriver();
		this.driver.navigate().to(getBaseUrl());
		new WebDriverWait(this.driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.className("cookie-alert-extended-button")));
		Helper.sleep(3000);
		this.driver.findElement(By.className("cookie-alert-extended-button")).click();
		List<WebElement> kategorije = this.driver.findElements(By.xpath("//*[@id=\"offers-overview-1\"]/ul/li/a"));
		kategorije.forEach(k -> {
			Category cat = Category.get(k.getText());
			if (cat != null) {
				this.kategorijeUrls.put(k.getAttribute("href"), cat);
			}
		});
		updateItems();
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
	public JavascriptExecutor js() {
		return this.driver.js();
	}
	
	@Override
	public void updateItems() throws Exception {
		List<Item> list = new ArrayList<>();
		this.kategorijeUrls.forEach((url, category) -> {
			this.driver.navigate().to(url);
			List<WebElement> elements = this.driver.findElements(By.className("o-overview-list__list-item"));
			for (WebElement element : elements) {
				js().executeScript(Scripts.SCRIPT_SCROLL_INTO_VIEW, element);
				WebElement button = element.findElement(By.className("a-button__container"));
				if (!"Otkrij više".equals(button.getText())) {
					String itemUrl = element.findElement(By.cssSelector("div div a")).getAttribute("href");
					String imageUrl = element.findElement(By.cssSelector("div div a div div figure img")).getAttribute("src");
					String[] params = imageUrl.split("/");
					String replacer = "/" + params[params.length - 2] + "/" + params[params.length - 1]; 
					imageUrl = imageUrl.replace(replacer, "/666x/detailImage.jpg");
					String priceString = element.findElement(By.className("a-pricetag__price")).getText();
					float price = Float.parseFloat(priceString.split("( kn)|(.-)")[0]);
					WebElement textElement = element.findElement(By.className("m-offer-tile__text"));
					String description = textElement.getText();
					String title = this.driver.findElement(element,
							By.className("m-offer-tile__title"),
							By.className("m-offer-tile__subtitle")
					).getText();
					Item item = new Item(imageUrl, itemUrl, getShopImageUrl(), category, price, title, description);
					list.add(item);
				}
			}
		});
		this.items = list;
	}

	@Override
	public void close() {
		this.driver.close();
	}

	@Override
	public String getShopImageUrl() {
		return "https://www.kaufland.hr/etc.clientlibs/kaufland/clientlibs/clientlib-klsite/resources/frontend/img/kl-logo-HR-937c8f6633.svg";
	}

	@Override
	public String getBaseUrl() {
		return "https://www.kaufland.hr/ponuda/ponuda-pregled.html";
	}
	
}
