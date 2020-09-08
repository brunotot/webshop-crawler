package com.crawler.shops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crawler.driver.IWebDriver;
import com.crawler.enums.Category;
import com.crawler.interfaces.WebShop;
import com.crawler.model.Item;

public class Konzum implements WebShop {
	
	private Map<String, Category> kategorijeUrls;
	
	private List<Item> items;

	private IWebDriver driver;
		
	public Konzum() throws Exception {
		this.items = new ArrayList<>();
		this.kategorijeUrls = new HashMap<>();
		this.driver = new IWebDriver();
		this.driver.navigate().to(getBaseUrl());
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"default\"]/header/div[3]/div/div[1]/div[1]/div/div/nav/div/ul/li")));
		this.driver.findElement(By.xpath("//*[@id=\"default\"]/header/div[3]/div/div[1]/div[1]/button")).click();
		this.driver.findElement(By.xpath("//*[@id=\"default\"]/header/div[3]/div/div[1]/div[1]/div/div/nav/button")).click();
		List<WebElement> kategorije = this.driver.findElements(By.xpath("//*[@id=\"default\"]/header/div[3]/div/div[1]/div[1]/div/div/nav/div/ul/li/a"));
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
	public void updateItems() throws Exception {
		List<Item> list = new ArrayList<>();
		this.kategorijeUrls.forEach((url, category) -> {
			this.driver.navigate().to(url);
			List<WebElement> subCategories = this.driver.findElements(By.xpath("//*[@id=\"content-start\"]/section/div/div/div[2]/div"));
			List<String> urls = new ArrayList<>();
			for (int i = 0; i < subCategories.size(); i += 3) {
				WebElement div = subCategories.get(i);
				String href = div.findElement(By.cssSelector("div > h1 > a")).getAttribute("href");
				urls.add(href + "?sort%5B%5D=&per_page=1000");
			}
			for (String u : urls) {
				this.driver.navigate().to(u);
				new WebDriverWait(this.driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"content-start\"]/section/div/div/div[2]/div[2]/article")));
				List<WebElement> listElements = this.driver.findElements(By.xpath("//*[@id=\"content-start\"]/section/div/div/div[2]/div[2]/article"));
				for (WebElement article : listElements) {
					new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(article));
					((JavascriptExecutor) driver).executeScript(IWebDriver.SCRIPT_SCROLL_INTO_VIEW, article);
					String imageUrl = article.findElement(By.xpath("div/div/div/a/img")).getAttribute("src");
					imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf('/')) + "/57ed05bea98bceae5f0eaada26b69cee6c61471d3030f7123d212844a35eba04";
					WebElement aElem = article.findElement(By.xpath("div/div/div/a"));
					String itemUrl = aElem.getAttribute("href");
					String title = article.findElement(By.xpath("div/div/div[2]/h4/a")).getText();
					String[] priceParams = article.findElement(By.xpath("div/div[3]/div[1]/div[1]")).getText().split("\n");
					String priceString = priceParams[0] + "." + priceParams[1];
					float price = Float.parseFloat(priceString);
					String description = this.getDescription(aElem);
					Item item = new Item(imageUrl, itemUrl, getShopImageUrl(), category, price, title, description);
					this.items.add(item);
				}
			}
		});
		this.items = list;
	}
	
	private String getDescription(WebElement element) {
		String firstHandle = this.driver.getWindowHandle();
		driver.openInNewTab(element);
		WebElement descElem = null;
		try {
			descElem = this.driver.findElement(By.tagName("dl"));
		} catch (Exception e) {
			// No description
			driver.close();
			driver.switchTo().window(firstHandle);
			return "";
		}
		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(descElem));
		String description = descElem.getText();		
		driver.close();
		driver.switchTo().window(firstHandle);
		return description;
	}
	
	@Override
	public void close() {
		this.driver.close();
	}

	@Override
	public JavascriptExecutor js() {
		return (JavascriptExecutor) this.driver;
	}

	@Override
	public String getBaseUrl() { 
		return "https://www.konzum.hr/";

	}

	@Override
	public String getShopImageUrl() {
		return "https://www.konzum.hr/assets/1i0/frontend/facebook/facebook_meta_image-5b88c5da1a557eaf6501d1fb63f883285f9346300d9b2e0a196dc32047a9542a.png";
	}
	
}
