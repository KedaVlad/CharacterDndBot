package com.dnd.CharacterDndBot.dnd.util.pools;

public class Matrix {

	private boolean[][] matrix;

	public void use(int key) {
		for (int i = matrix[key].length; i >= 0; i--) {
			if (matrix[key][i] == true) {
				matrix[key][i] = false;
				break;
			}
		}
	}

	public int value(int key) {
		int value = 0;
		for (boolean block : matrix[key]) {
			if (block == true) {
				value++;
			} else {
				break;
			}
		}
		return value;
	}

	public void refresh() {
		for (boolean[] arr : matrix) {
			for (boolean block : arr) {
				if (block == false) {
					block = true;
				}
			}
		}
	}

	public void refresh(int key, int value) {
		for (boolean block : matrix[key]) {
			if (block == false && value > 0) {
				block = true;
				value--;
			}

		}
	}

	public boolean[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(boolean[][] matrix) {
		this.matrix = matrix;
	}

}
