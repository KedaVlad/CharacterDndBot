package app.dnd.service.logic.talants.prof;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.proficiency.Possession;
import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.model.enums.Proficiency;

@Component
class ProficienciesAddPossession {
	
	@Autowired
	private ProficienciesService proficienciesService;
	
	public void add(Long id, Possession possession) {
		
		Proficiencies proficiencies = proficienciesService.getById(id);
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
