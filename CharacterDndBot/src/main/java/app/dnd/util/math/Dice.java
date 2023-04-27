package app.dnd.util.math;


import app.dnd.model.enums.Roll;
import lombok.Data;

@Data
public class Dice {

	private String name;
	private int buff;
	private Roll[] combo;
	private int[] results;

	public String toString() {
		StringBuilder answer = new StringBuilder();
		for (Roll roll : combo) {
			answer.append(roll).append(" ");
		}
		answer.append("(+").append(buff).append(")");
		return answer.toString();
	}

	public Dice() {};

	public Dice(String name, int buff, Roll... combo) {
		this.setName(name);
		this.setBuff(buff);
		this.setCombo(combo);
		results = new int[combo.length + 1];
	}

	public String execute() {
		StringBuilder answer = new StringBuilder(this.name).append(": ").append(roll());

		if (results.length > 2 || results.length > 1 && results[0] != 0 && results[1] != 0) {
			answer.append("(");
			boolean start = true;
			for (int target : results) {
				if (start && (target != 0)) {
					answer.append(target);
					start = false;
				} else if (target < 0) {
					answer.append(" - ").append(target * -1);
				} else if (target > 0) {
					answer.append(" + ").append(target);
				}
			}

			answer.append(")");
		}
		return answer.toString();
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
		System.arraycopy(rolls, 0, result, 0, rolls.length);
		int treker = 0;
		for (int i = rolls.length; i < result.length; i++) {
			result[i] = combo[treker];
			treker++;
		}
		combo = result;
	}

	public void addRollToEnd(Roll... rolls) {
		Roll[] result = new Roll[combo.length + rolls.length];
		System.arraycopy(combo, 0, result, 0, combo.length);
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
