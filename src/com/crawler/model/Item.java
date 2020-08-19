package com.crawler.model;

import com.crawler.enums.Category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

	private String imageUrl;
	
	private String itemUrl;
	
	private String shopImageUrl;
	
	private Category category;
	
	private float price;
	
	private String title;
	
	private String description;
	
}
