package com.dnd.CharacterDndBot.dnd.service.logic.proficiencies;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Proficiencies.Proficiency;

@Component
public class ProficienciesAddPossession {

	public void add(Proficiencies proficiencies, Possession possession) {
		for (Possession target : proficiencies.getPossessions()) {
			if (target.getName().contains(possession.getName())) {
				target.setProf(upOrStay(target.getProf(), possession.getProf()));
				return;
			}
		}
		proficiencies.getPossessions().add(possession);
	}

	private Proficiency upOrStay(Proficiency first, Proficiency second) {
		if (second.equals(Proficiency.COMPETENSE)) {
			return Proficiency.COMPETENSE;
		} else if (first.equals(Proficiency.BASE)) {
			return Proficiency.BASE;
		} else {
			return second;
		}
	}
}
