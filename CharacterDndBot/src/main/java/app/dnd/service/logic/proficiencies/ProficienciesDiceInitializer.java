package app.dnd.service.logic.proficiencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.service.ActualHeroService;
import app.dnd.dto.ability.proficiency.Proficiencies;
import app.dnd.dto.ability.proficiency.Proficiencies.Proficiency;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;

@Component
public class ProficienciesDiceInitializer {

	@Autowired
	private ActualHeroService actualHeroService;
	
	public Dice init(Long id, Proficiency prof) {
		Proficiencies proficiencies = actualHeroService.getById(id).getCharacter().getAbility().getProficiencies();
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

