package com.crawler.interfaces;

import java.util.List;
import java.util.Map;

import com.crawler.enums.Category;
import com.crawler.model.Item;

public interface WebShop {

	List<Item> getItemsFromCategory(Category category);
	
	List<Item> getItems();

	void updateItemsList() throws Exception;
	
	void close();
	
	Map<String, Category> getKategorijeUrls();
}
