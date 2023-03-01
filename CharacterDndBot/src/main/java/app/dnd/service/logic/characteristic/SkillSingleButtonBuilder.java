package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.Skill;
import app.dnd.dto.characteristics.Stat;
import app.dnd.service.logic.proficiencies.ProficienciesValueInitializer;

@Component
public class SkillSingleButtonBuilder {

	@Autowired
	private StatModificator statModificator;
	@Autowired
	private ProficienciesValueInitializer proficienciesValueInitializer;

	public String build(Skill skill, CharacterDnd character) {

		String answer = "ERROR";
		for (Stat stat : character.getCharacteristics().getStats()) {
			if (stat.getName() == skill.getDepends()) {
				if (skill.getProficiency() != null) {
					answer =  statModificator.modificate(stat) + proficienciesValueInitializer.getProf(character.getAbility().getProficiencies(), skill.getProficiency()) + "";
					return answer + " " + skill.getName();
				} else {
					answer = statModificator.modificate(stat) + " ";
					return answer + " " + skill.getName();
				}
			}
		}
		return answer;	
	}
}

