package com.dnd.CharacterDndBot.dnd.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.dnd.util.ArrayToThreeColums;

class ArrayToThreeColumsTest {
	
	ArrayToThreeColums arrayToThreeColums;
	Object[] objects;
	String[][] buttons;
	@BeforeEach
	void setUp() throws Exception {
		objects = new String[] {"One", "Two", "Three", "Four"};
		buttons = new String[][] {{"One", "Two", "Three"},{"Four"}};
		arrayToThreeColums = new ArrayToThreeColums();
	}

	@Test
	void test() {
		for(String[] arr: buttons) {
			System.out.println(" Button need");
			for(String str:arr){
				System.out.print(str);
			}
		}
		
		String[][] buttonIs = arrayToThreeColums.rebuild(objects);
		for(String[] arr: buttonIs) {
			System.out.println(" Button is");
			for(String str:arr){
				System.out.print(str);
			}
		}

		assertEquals(buttons.length, arrayToThreeColums.rebuild(objects).length);
	}

}
