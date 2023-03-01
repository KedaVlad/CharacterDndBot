package app.dnd.service.logic.proficiencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.ActualHero;
import app.bot.service.ActualHeroService;
import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.ability.proficiency.Proficiencies;
import app.dnd.dto.ability.proficiency.Proficiencies.Proficiency;

@Component
public class ProficienciesAddPossession {

	@Autowired
	private ActualHeroService actualHeroService;
	
	public void add(Long id, Possession possession) {
		
		ActualHero actualHero = actualHeroService.getById(id);
		Proficiencies proficiencies = actualHero.getCharacter().getAbility().getProficiencies();
		add(proficiencies, possession);
		actualHeroService.save(actualHero);
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
