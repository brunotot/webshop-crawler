package com.crawler.util;

public class Helper {

	public static String replaceCroatianLetters(String croatianString) {
		String englishString = croatianString;
		englishString = englishString.replace("Č", "C");
		englishString = englishString.replace("č", "c");
		englishString = englishString.replace("Ć", "C");
		englishString = englishString.replace("ć", "c");
		englishString = englishString.replace("Š", "S");
		englishString = englishString.replace("š", "s");
		englishString = englishString.replace("Đ", "D");
		englishString = englishString.replace("đ", "d");
		englishString = englishString.replace("Ž", "Z");
		englishString = englishString.replace("ž", "z");
		return englishString;
	}
	
	public static String removeLeadingWhitespaces(String line) {
		String newLine = line + "";
		while (newLine.charAt(0) == ' ' || newLine.charAt(0) == '\t' || newLine.charAt(0) == '\n') {
			newLine = newLine.substring(1);
		}
		while (newLine.charAt(newLine.length() - 1) == ' ' || newLine.charAt(newLine.length() - 1) == '\t' || newLine.charAt(newLine.length() - 1) == '\n') {
			newLine = newLine.substring(0, newLine.length() - 1);
		}
		return newLine;
	}
	
}
