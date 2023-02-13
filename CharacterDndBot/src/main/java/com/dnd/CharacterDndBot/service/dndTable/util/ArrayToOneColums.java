package com.dnd.CharacterDndBot.service.dndTable.util;

import org.springframework.stereotype.Component;

@Component
public class ArrayToOneColums {

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

		String[][] buttons = new String[all.length][1];

		for (int i = 0; i < all.length; i++) {
			buttons[i][0] = all[i];
		}

		return buttons;
	}
}