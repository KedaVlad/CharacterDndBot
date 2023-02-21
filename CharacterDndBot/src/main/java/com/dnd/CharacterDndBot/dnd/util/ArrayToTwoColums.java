package com.dnd.CharacterDndBot.dnd.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ArrayToTwoColums {

	public String[][] rebuild(Object[] array) {

		String[] all;
		if (array instanceof String[]) {
			all = (String[]) array;
		} else {
			all = new String[array.length];
			for (int i = 0; i < all.length; i++) {
				all[i] = array[i].toString();
			}
		}
		List<String[]> buttons = new ArrayList<>();

		for (int i = 1; i <= all.length; i += 2) {
			if ((i + 1) > all.length) {
				buttons.add(new String[] { all[i - 1] });
			} else {
				buttons.add(new String[] { all[i - 1], all[i] });
			}
		}
		return buttons.toArray(String[][]::new);
	}
}