package app.dnd.service.logic.proficiencies;

import org.springframework.stereotype.Component;

import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.ability.proficiency.Proficiencies;
import app.dnd.dto.ability.proficiency.Proficiencies.Proficiency;

@Component
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
