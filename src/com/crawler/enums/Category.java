package com.crawler.enums;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public enum Category {
	MESO_RIBA,
	MLIJECNI_PROIZVODI,
	SMRZNUTI_PROIZVODI,
	VOCE_POVRCE,
	NAPITCI,
	OSNOVNE_NAMIRNICE,
	DELIKATESE,
	OSTALO,
	UMACI_ZACINI,
	KUCANSTVO, 
	KAVA_CAJ_SLATKISI,
	DROGERIJA_LJUBIMCI,
	PRIPREMA_JELA,
	NJEGA_HIGIJENA,
	DJECA,
	PARTY,
	AUTOMOBILI,
	SKOLA_URED,
	NEKRETNINE,
	NAUTIKA,
	TURIZAM,
	USLUGE,
	ELEKTRONIKA,
	ODJECA_OBUCA_I_MODNI_DODACI,
	POSAO,
	SPORT_I_OPREMA,
	GLAZBALA_I_LITERATURA,
	ALATI,
	IZGUBLJENO_PRONADENO,
	HRANA_OPCENITO,
	LJETO;
	
	private static Map<Category, List<String>> map;
	static {
		map = new HashMap<>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is = new FileInputStream(new File("resources/categories.xml"));
			Document doc = builder.parse(is);
			NodeList categories = doc.getElementsByTagName("category");
			for (int i = 0; i < categories.getLength(); i++) {
	            Node category = categories.item(i);
	            if (category.getNodeType() == Node.ELEMENT_NODE) {
	            	Element categoryElement = (Element) category;
	                String categoryValue = categoryElement.getAttribute("value");
	            	NodeList attributes = categoryElement.getElementsByTagName("attribute");
	                for (int j = 0; j < attributes.getLength(); j++) {
	                	String attributeValue = attributes.item(j).getTextContent();
	                	add(categoryValue, attributeValue);
	                }
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void add(String category, String attribute) {
		Category c = Category.valueOf(category);
		if (c == null) {
			return;
		}
		List<String> attributes = map.get(c);
		if (attributes == null) {
			attributes = new ArrayList<>();
		}
		attributes.add(attribute);
		map.put(c, attributes);
	}
	
	public static Category get(String shopSpecificCategory) {
		for (Map.Entry<Category, List<String>> entry : map.entrySet()) {
			Category category = entry.getKey();
			List<String> shopSpecificCategoryNames = entry.getValue();
			for (String shopSpecificCategoryName : shopSpecificCategoryNames) {
				if (shopSpecificCategoryName.equals(shopSpecificCategory)) {
					return category;
				}
			}
		}
		return null;
	}
	
}
