package com.crawler.util;

public class Helper {
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
