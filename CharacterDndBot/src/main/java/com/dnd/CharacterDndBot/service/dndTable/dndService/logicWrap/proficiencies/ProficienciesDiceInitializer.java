package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.proficiencies;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Proficiencies.Proficiency;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer.Roll;

@Component
public class ProficienciesDiceInitializer {

	public Dice init(Proficiencies proficiencies, Proficiency prof) {
		Dice answer = new Dice("Proficiency", 0, Roll.NO_ROLL);
		switch(prof) {
		case HALF:
			answer.setBuff(proficiencies.getProficiency() / 2);
			break;
		case BASE:
			answer.setBuff(proficiencies.getProficiency());
			break;
		case COMPETENSE:
			answer.setBuff(proficiencies.getProficiency() * 2);
			break;
		}
		return answer;
	}
}

