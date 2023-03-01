package app.dnd.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayToColumnsTest {

	ArrayToColumns arrayToThreeColumns;
	String[] objects;
	String[][] buttons;
	
	@BeforeEach
	void setUp() throws Exception {
		objects = new String[] {"One", "Two", "Three", "Four"};
		buttons = new String[][] {{"One", "Two", "Three"},{"Four"}};
		arrayToThreeColumns = new ArrayToColumns();
	}

	@Test
	void testText() {
		assertEquals(buttons[0][0], arrayToThreeColumns.rebuild(objects,3, String.class)[0][0]);
	}

	@Test
	void testOrganization() {
		assertEquals(arrayToThreeColumns.rebuild(objects,3,String.class)[1].length, 1);
	}
	
	@Test
	void testSmallOrganization() {
		objects = new String[] {"One", "Two"};
		String target = arrayToThreeColumns.rebuild(objects,3,String.class)[0][0];
		String[][] target2 = arrayToThreeColumns.rebuild(objects,3,String.class);
		assertEquals(arrayToThreeColumns.rebuild(objects,3,String.class)[0].length, 2);
	}
	

}
