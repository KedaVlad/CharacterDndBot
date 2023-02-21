package com.dnd.CharacterDndBot.dnd.service.logic.proficiencies;

import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies.Proficiency;

public class ProficienciesFinder {

	public Proficiency get(Proficiencies proficiencies, String name) {
		for(Possession target: proficiencies.getPossessions()) {
			if(target.getName().contains(name)) {
				return target.getProf();
			}
		}
		return null;
	}

	public boolean check(Proficiencies proficiencies, String name) {
		for(Possession target: proficiencies.getPossessions()) {
			if(target.getName().contains(name)) {
				return true;
			}
		}
		return false;
	}
}
