package com.dnd.CharacterDndBot.service.dndTable.dndMath;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Formalizer {
	
	public enum Roll {
		D4, D6, D8, D10, D12, D20, D100, NO_ROLL
	}

	public static String formalize(String string) {
		String firstFix = string.replaceAll("\\s", "").toLowerCase();
		if (firstFix.matches("^-") || firstFix.matches("^\\+")) {

		} else {
			firstFix = "+" + firstFix;
		}
		Pattern pattern = Pattern.compile("(\\+|-)[^\\+-]+");
		Matcher matcher = pattern.matcher(firstFix);
		List<Dice> dices = new ArrayList<>();
		while (matcher.find()) {
			if (buildDice(matcher.group()) == null) {
				return "There is a mistake in the dice roll formula, try again following the rules.";
			} else {
				dices.add(buildDice(matcher.group()));
			}
		}

		Formula formula = new Formula(dices.toArray(Dice[]::new));
		return formula.execute();
	}

	private static Roll buildRoll(String str) {
		switch (str) {
		case "d4":
			return Roll.D4;
		case "d6":
			return Roll.D6;
		case "d8":
			return Roll.D8;
		case "d10":
			return Roll.D10;
		case "d12":
			return Roll.D12;
		case "d20":
			return Roll.D20;
		case "d100":
			return Roll.D100;
		default:
			return Roll.NO_ROLL;
		}
	}

	private static Dice buildDice(String str) {
		Dice answer;
		if (str.matches("(\\+|-)(\\d+(d4|d6|d8|d10|d12|d20|d100))")) {
			int times = ((Integer) Integer.parseInt(str.replaceAll("(\\+|-)(\\d*)(d4|d6|d8|d10|d12|d20|d100)", "$2")));
			Roll roll = buildRoll(str.replaceAll("(\\+|-)(\\d*)(d4|d6|d8|d10|d12|d20|d100)", "$3"));

			Roll[] combo = new Roll[times];
			for (int i = 0; i < times; i++) {
				combo[i] = roll;
			}

			answer = new Dice(str, 0, combo);

			if (str.contains("-")) {
				answer.execute();
				return new Dice(str, answer.summ() * -1, Roll.NO_ROLL);
			} else {
				return answer;
			}

		} else if (str.matches("(\\+|-)(d4|d6|d8|d10|d12|d20|d100)")) {
			Roll roll = buildRoll(str.replaceAll("(\\+|-)(d4|d6|d8|d10|d12|d20|d100)", "$2"));
			answer = new Dice(str, 0, roll);

			if (str.contains("-")) {
				answer.execute();
				return new Dice(str, answer.summ() * -1, Roll.NO_ROLL);
			} else {
				return answer;
			}
		} else if (str.matches("(\\+|-)\\d+")) {
			int value = ((Integer) Integer.parseInt(str.replaceAll("(\\+|-)(\\d+)", "$2")));
			if (str.contains("-")) {
				return new Dice(str, value * -1, Roll.NO_ROLL);
			} else {
				return new Dice(str, value, Roll.NO_ROLL);
			}
		} else {
			return null;
		}
	}

	public static int roll(Roll roll) {
		switch (roll) {
		case D4:
			return d4();
		case D6:
			return d6();
		case D8:
			return d8();
		case D10:
			return d10();
		case D12:
			return d12();
		case D20:
			return d20();
		case D100:
			return d100();
		default:
			break;
		}

		return 0;
	}

	public static int d4() {
		return (int) Math.round(Math.random() * 3 + 1);
	}

	public static int d6() {
		return (int) Math.round(Math.random() * 5 + 1);
	}

	public static int d8() {
		return (int) Math.round(Math.random() * 7 + 1);
	}

	public static int d10() {
		return (int) Math.round(Math.random() * 9 + 1);
	}

	public static int d12() {
		return (int) Math.round(Math.random() * 11 + 1);
	}

	public static int d20() {
		return (int) Math.round(Math.random() * 19 + 1);
	}

	public static int d100() {
		return (int) Math.round(Math.random() * 99 + 1);
	}

	public static int randomStat() {

		int[] allDice = { d6(), d6(), d6(), d6() };
		int answer = 0;
		int smaller = 0;

		for (int i = 0; i < allDice.length; i++) {
			if (i == 0) {
				smaller = allDice[i];

			} else if (smaller < allDice[i]) {
				answer += allDice[i];
			} else {
				answer += smaller;
				smaller = allDice[i];
			}
		}
		return answer;
	}

}
