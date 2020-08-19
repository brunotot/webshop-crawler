package com.crawler.app;

import com.crawler.interfaces.WebShop;
import com.crawler.shops.Kaufland;

public class Main {

	public static void main(String[] args) {
		WebShop kaufland = null;
		try {
			kaufland = new Kaufland();
			kaufland.updateItemsList();
			kaufland.getItems().forEach(item -> {
				System.out.println(item.getTitle());
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (kaufland != null) {
				kaufland.close();
			}
		}
	}

}
