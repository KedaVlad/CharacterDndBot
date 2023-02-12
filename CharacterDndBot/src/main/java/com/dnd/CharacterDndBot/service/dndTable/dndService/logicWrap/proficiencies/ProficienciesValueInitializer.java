package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.proficiencies;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies.Proficiency;

@Component
public class ProficienciesValueInitializer {
	
	public int getProf(Proficiencies proficiencies, Proficiency prof) {
		switch (prof) {
		case BASE:
			return proficiencies.getProficiency();
		case COMPETENSE:
			return proficiencies.getProficiency() * 2;
		case HALF:
			return proficiencies.getProficiency() / 2;
		default:
			return 0;
		}
	}
}
