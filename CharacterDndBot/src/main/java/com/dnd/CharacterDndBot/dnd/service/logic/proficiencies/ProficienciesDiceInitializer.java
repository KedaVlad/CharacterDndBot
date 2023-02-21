package com.dnd.CharacterDndBot.dnd.service.logic.proficiencies;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies.Proficiency;
import com.dnd.CharacterDndBot.dnd.util.math.Dice;
import com.dnd.CharacterDndBot.dnd.util.math.Formalizer.Roll;

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

