package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.proficiencies;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies.Proficiency;

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
