package com.dnd.CharacterDndBot.dnd.util.math;

 
import com.dnd.CharacterDndBot.dnd.util.math.Formalizer.Roll;

import lombok.Data;

@Data
public class Formula {

	private String name = "";
	private Dice[] formula;

	private boolean natural20;
	private boolean natural1;

	private enum CritCheck {
		NONE, CRIT20, CRIT1
	}

	public String toString() {
		String answer = "";
		for (Dice dice : formula) {
			for (Roll roll : dice.getCombo()) {
				answer += roll.toString();
			}
			answer += dice.getBuff() + " " + dice.getName() + " |||";
		}
		return answer;
	}

	public Formula(Dice... formula) {
		this.formula = formula;
	}

	public Formula(String name, Dice... formula) {
		this.name = name + "\n";
		this.formula = formula;
	}

	private CritCheck critCheck(Dice dice) {
		if (dice.getCombo()[0].equals(Roll.D20)) {
			if (dice.getResults()[0] == 20) {
				return CritCheck.CRIT20;
			} else if (dice.getResults()[0] == 1) {
				return CritCheck.CRIT1;
			} else {
				return CritCheck.NONE;
			}
		} else {
			return CritCheck.NONE;
		}
	}

	public String execute(boolean advanture) {
		String answer = this.name;
		int first;
		CritCheck critFirst;
		CritCheck critSecond;
		CritCheck crit;

		this.name = "First roll\n\n";
		answer += this.execute() + "\n";
		first = this.summ();
		critFirst = critCheck(this.formula[0]);

		this.name = "\nSecond roll\n\n";
		answer += this.execute() + "\n";
		critSecond = critCheck(this.formula[0]);

		if (advanture) {
			if (this.summ() > first) {
				answer += "\nFinal result: " + this.summ();
				crit = critSecond;
			} else {
				answer += "\nFinal result: " + first;
				crit = critFirst;
			}
		} else {
			if (this.summ() < first) {
				answer += "\nFinal result: " + this.summ();
				crit = critSecond;
			} else {
				answer += "\nFinal result: " + first;
				crit = critFirst;
			}
		}
		if (crit.equals(CritCheck.CRIT1)) {
			this.natural1 = true;
			answer += " !CRITICAL 1!";
		} else if (crit.equals(CritCheck.CRIT20)) {
			this.natural20 = true;
			answer += " !NATURAL 20!";
		}
		return answer;
	}

	public String execute() {
		String answer = name;

		for (Dice dice : getFormula()) {
			answer += dice.execute() + "\n";
		}
		answer += "Result: " + summ();
		if (formula[0].getCombo() != null && formula[0].getCombo()[0].equals(Roll.D20)) {
			if (critCheck(formula[0]).equals(CritCheck.CRIT20)) {
				this.natural20 = true;
				answer += " !NATURAL 20!";
			} else if (critCheck(formula[0]).equals(CritCheck.CRIT1)) {
				this.natural1 = true;
				answer += " !CRITICAL 1!";
			}
		}
		return answer;
	}

	public int summ() {
		int result = 0;
		for (Dice dice : getFormula()) {
			result += dice.summ();
		}
		return result;
	}

	public void addDicesToStart(Dice... dices) {
		Dice[] result = new Dice[formula.length + dices.length];
		for (int i = 0; i < dices.length; i++) {
			result[i] = dices[i];
		}
		int treker = 0;
		for (int i = dices.length; i < result.length; i++) {
			result[i] = formula[treker];
			treker++;
		}
		formula = result;
	}

	public void addDicesToEnd(Dice... dices) {
		Dice[] result = new Dice[formula.length + dices.length];
		for (int i = 0; i < formula.length; i++) {
			result[i] = formula[i];
		}
		int treker = 0;
		for (int i = formula.length; i < result.length; i++) {
			result[i] = dices[treker];
			treker++;
		}
		formula = result;
	}

}