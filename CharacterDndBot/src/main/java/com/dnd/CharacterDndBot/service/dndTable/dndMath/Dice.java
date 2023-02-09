package com.dnd.CharacterDndBot.service.dndTable.dndMath;
 
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer.Roll;

import lombok.Data;

@Data
public class Dice {
	 
	private String name;
	private int buff;
	private Roll[] combo;
	private int[] results;

	public String toString() {
		String answer = "";
		for (Roll roll : combo) {
			answer += roll + " ";
		}
		answer += "(+" + buff + ")";
		return answer;
	}

	public Dice() {};

	public Dice(String name, int buff, Roll... combo) {
		this.setName(name);
		this.setBuff(buff);
		this.setCombo(combo);
		results = new int[combo.length + 1];
	}

	public String execute() {
		String answer = this.name + ": " + roll();

		if (results.length > 2 || results.length > 1 && results[0] != 0 && results[1] != 0) {
			answer += "(";
			boolean start = true;
			for (int i = 0; i < results.length; i++) {
				int target = results[i];
				if (start && (target != 0)) {
					answer += "" + target;
					start = false;
				} else if (target < 0) {
					answer += " - " + target * -1;
				} else if (target > 0) {
					answer += " + " + target;
				} else {
					continue;
				}

			}

			return answer + ")";
		}
		return answer;
	}

	public int roll() {
		for (int i = 0; i < this.getCombo().length; i++) {
			this.getResults()[i] = Formalizer.roll(this.getCombo()[i]);
		}
		this.getResults()[this.getResults().length - 1] = this.buff;
		return summ();
	}

	int summ() {
		int answer = 0;
		for (int target : getResults()) {
			answer += target;
		}
		return answer;
	}

	public void addRollToStart(Roll... rolls) {
		Roll[] result = new Roll[combo.length + rolls.length];
		for (int i = 0; i < rolls.length; i++) {
			result[i] = rolls[i];
		}
		int treker = 0;
		for (int i = rolls.length; i < result.length; i++) {
			result[i] = combo[treker];
			treker++;
		}
		combo = result;
	}

	public void addRollToEnd(Roll... rolls) {
		Roll[] result = new Roll[combo.length + rolls.length];
		for (int i = 0; i < combo.length; i++) {
			result[i] = combo[i];
		}
		int treker = 0;
		for (int i = combo.length; i < result.length; i++) {
			result[i] = rolls[treker];
			treker++;
		}
		combo = result;
	}

	public void setCombo(Roll... combo) {
		this.combo = combo;
	}

}
