package com.crawler.app;

import com.crawler.interfaces.WebShop;
import com.crawler.shops.Kaufland;
import com.crawler.shops.Konzum;

public class Main {

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			WebShop konzum = null;
			WebShop kaufland = null;
			try {
				konzum = new Konzum();
				kaufland = new Kaufland();
				konzum.getItems().forEach(item -> System.out.println(item.getTitle()));
				kaufland.getItems().forEach(item -> System.out.println(item.getTitle()));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (konzum != null) {
					konzum.close();
				}
				if (kaufland != null) {
					kaufland.close();
				}
			}
		}
	}

}
