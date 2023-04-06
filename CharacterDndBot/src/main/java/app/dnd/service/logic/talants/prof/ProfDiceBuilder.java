package app.dnd.service.logic.talants.prof;

import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.model.enums.Proficiency;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;

public class ProfDiceBuilder {

	@Autowired
	private ProficienciesService proficienciesService;

	public Dice build(Long id, Proficiency prof) {

		Proficiencies proficiencies = proficienciesService.getById(id);

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
