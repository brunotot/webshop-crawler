package com.crawler.enums;

import java.util.HashMap;
import java.util.Map;

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
	HRANA_OPCENITO,
	LJETO;
	
	private static Map<Category, String[]> map;
	static {
		map = new HashMap<>();
		map.put(Category.MESO_RIBA, new String[] { "Meso i riba", "Meso, perad, kobasice", "Svježa riba" });
		map.put(Category.MLIJECNI_PROIZVODI, new String[] { "Mliječni proizvodi i jaja", "Mliječni proizvodi" });
		map.put(Category.SMRZNUTI_PROIZVODI, new String[] { "Smrznuti proizvodi", "Smrznuta hrana" });
		map.put(Category.VOCE_POVRCE, new String[] { "Voće i povrće", "Voće, povrće, biljke" });
		map.put(Category.NAPITCI, new String[] { "Pića", "Napitci, alkoholna pića" });
		map.put(Category.OSNOVNE_NAMIRNICE, new String[] { "Osnovne živežne namirnice" });
		map.put(Category.DELIKATESE, new String[] { "Delikatesa", "Delikatese, konzerve", "Konzervirano i juhe" });
		map.put(Category.OSTALO, new String[] { "Odjeća, oprema za automobil, slobodno vrijeme, igračke", "Ostalo", "Na otvorenom" });
		map.put(Category.UMACI_ZACINI, new String[] { "Umaci i začini" });
		map.put(Category.KUCANSTVO, new String[] { "Sve za kućanstvo", "Elektrouređaji, uredski pribor, multimedija", "Posuđe", "Kućanske potrepštine" }); 
		map.put(Category.KAVA_CAJ_SLATKISI, new String[] { "Kava, čaj, slatkiši, grickalice", "Slatkiši i grickalice", "Pahuljice, namazi, kave, čajevi" });
		map.put(Category.DROGERIJA_LJUBIMCI, new String[] { "Drogerija, hrana za kućne ljubimce", "Kućni ljubimci" });
		map.put(Category.PRIPREMA_JELA, new String[] { "Priprema jela", "Tjestenina, riža, njoki, tortilje", "Priprema kolača" });
		map.put(Category.NJEGA_HIGIJENA, new String[] { "Njega i higijena", "Čišćenje i pospremanje" });
		map.put(Category.DJECA, new String[] { "Dječji svijet" });
		map.put(Category.PARTY, new String[] { "Party asortiman" });
		map.put(Category.AUTOMOBILI, new String[] { "Auto program" });
		map.put(Category.SKOLA_URED, new String[] { "Škola i ured" });
		map.put(Category.HRANA_OPCENITO, new String[] { "Pazim što jedem", "OPG proizvodi", "Pekarnica", "Brzi obrok" });
		map.put(Category.LJETO, new String[] { "Ljeto" });
	}
	
	public static Category get(String shopSpecificCategory) {
		for (Map.Entry<Category, String[]> entry : map.entrySet()) {
			Category category = entry.getKey();
			String[] shopSpecificCategoryNames = entry.getValue();
			for (String shopSpecificCategoryName : shopSpecificCategoryNames) {
				if (shopSpecificCategoryName.equals(shopSpecificCategory)) {
					return category;
				}
			}
		}
		return null;
	}
	
}
