package com.crawler.interfaces;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;

import com.crawler.enums.Category;
import com.crawler.model.Item;

public interface WebShop {

	String getBaseUrl();
	
	String getShopImageUrl();
	
	List<Item> getItems(Category category);
	
	List<Item> getItems();

	void updateItems() throws Exception;
	
	void close();
	
	JavascriptExecutor js();
	
}
