package app.dnd.util;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class ArrayToColumns {

	
	public <T> T[][] rebuild(T[] arr, int columns, Class<T> clazz) {
		
		int rows = (int) Math.ceil((double) arr.length / columns);
		
		@SuppressWarnings("unchecked")
		T[][] result = (T[][]) Array.newInstance(clazz, rows, columns);

		for (int i = 0; i < arr.length; i++) {
			int row = i / columns;
			int col = i % columns;
			result[row][col] = arr[i];
		}

		int lastRowLength = arr.length % columns;
		if (lastRowLength != 0) {
			T[] lastRow = result[rows - 1];
			T[] resizedLastRow = Arrays.copyOf(lastRow, lastRowLength);
			result[rows - 1] = resizedLastRow;
		}
		return result;
	}
}