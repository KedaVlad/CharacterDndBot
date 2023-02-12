package com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics;


import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;

import lombok.Data;

@Data
public class Stat implements ObjectDnd {

	private static final long serialVersionUID = 1L;
	private Stats name;
	private int value = 0;
	private int maxValue = 20;
	private int[] elseSourceValue = { 0, 0 };

	public Stat(Stats name) {
		this.name = name;
	}

	public enum Stats {
		STRENGTH("Strength"), DEXTERITY("Dexterity"), CONSTITUTION("Constitution"), INTELLIGENSE("Intelligense"),
		WISDOM("Wisdom"), CHARISMA("Charisma");

		private String name;

		Stats(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
}
