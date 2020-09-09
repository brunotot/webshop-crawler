package com.crawler.app;

import com.crawler.interfaces.WebShop;
import com.crawler.model.Item;
import com.crawler.shops.Konzum;

public class Main {

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			WebShop konzum = null;
			WebShop kaufland = null;
			try {
				konzum = new Konzum();
//				kaufland = new Kaufland();
				for (Item item : konzum.getItems())
					System.out.println(item.getTitle());
//				for (Item item : kaufland.getItems())
//					System.out.println(item.getTitle());
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
