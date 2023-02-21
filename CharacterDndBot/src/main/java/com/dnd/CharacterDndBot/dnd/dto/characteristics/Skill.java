package com.dnd.CharacterDndBot.dnd.dto.characteristics;
 
import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.dnd.dto.ObjectDnd;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies.Proficiency;
import com.dnd.CharacterDndBot.dnd.dto.characteristics.Stat.Stats;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Skill extends ObjectDnd {

	private static final long serialVersionUID = 1L;
	private String name;
	private Stats depends;
	private Proficiency proficiency;
	private List<String> spesial;

	public enum Skills {
		ACROBATICS("Acrobatics"), ANIMAL_HANDING("Animal Handing"), ARCANA("Arcana"), ATHLETIX("Athletix"),
		DECEPTION("Deception"), HISTORY("History"), INSIGHT("Insight"), INTIMIDATION("Intimidation"),
		INVESTIGATION("Investigation"), MEDICINE("Medicine"), NATURE("Nature"), PERCEPTION("Perception"),
		PERFORMANCE("Performance"), PERSUASION("Persuasion"), RELIGION("Religion"), SLEIGHT_OF_HAND("Sleigth of Hand"),
		STELTH("Stelth"), SURVIVAL("Survival");

		String name;

		Skills(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

	}

	public Skill(String name, Stats depends) {
		this.name = name;
		this.depends = depends;
		this.spesial = new ArrayList<>();
	}
}
