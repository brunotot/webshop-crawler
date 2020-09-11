package com.crawler.model;

import com.crawler.enums.Category;

public class Item {

	private String imageUrl;
	
	private String itemUrl;
	
	private String shopImageUrl;
	
	private Category category;
	
	private float price;
	
	private String title;
	
	private String description;

	public Item(String imageUrl, String itemUrl, String shopImageUrl, Category category, float price, String title,
			String description) {
		super();
		this.imageUrl = imageUrl;
		this.itemUrl = itemUrl;
		this.shopImageUrl = shopImageUrl;
		this.category = category;
		this.price = price;
		this.title = title;
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public String getShopImageUrl() {
		return shopImageUrl;
	}

	public Category getCategory() {
		return category;
	}

	public float getPrice() {
		return price;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public void setShopImageUrl(String shopImageUrl) {
		this.shopImageUrl = shopImageUrl;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
