package app.dnd.service.logic.proficiencies;

import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.ability.proficiency.Proficiencies;
import app.dnd.dto.ability.proficiency.Proficiencies.Proficiency;

@Component
public class ProficienciesAddPossession {
	
	public void add(CharacterDnd character, Possession possession) {
		
		Proficiencies proficiencies = character.getAbility().getProficiencies();
		add(proficiencies, possession);
	}
	
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
