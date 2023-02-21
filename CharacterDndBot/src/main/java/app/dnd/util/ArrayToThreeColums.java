package app.dnd.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ArrayToThreeColums {

	public String[][] rebuild(Object[] array) {

		log.info("ArrayToThreeColums : " + array.length);
		String[] all;
		if (array instanceof String[]) {
			log.info("ArrayToThreeColums (array instanceof String[]) : " + array.length);
			all = (String[]) array;
		} else {
			all = new String[array.length];
			for (int i = 0; i < all.length; i++) {
				all[i] = array[i].toString();
			}
		}
		log.info("ArrayToThreeColums (array string) : " + all.length);
		List<String[]> buttons = new ArrayList<>();

		for (int i = 1; i <= all.length; i += 3) {
			if ((i + 1) > all.length) {
				buttons.add(new String[] { all[i - 1] });
			} else if ((i + 2) > all.length) {
				buttons.add(new String[] { all[i - 1], all[i] });
			} else {
				buttons.add(new String[] { all[i - 1], all[i], all[i + 1] });
			}
		}
		log.info("ArrayToThreeColums (Buttoms) : " + buttons.size());
		
		String[][] answer = buttons.toArray(String[][]::new);
		for(String[] arr: answer) {
			for(String str:arr) {
				log.info("button: " + str);
			}
		}
		
		return answer;
	}
}