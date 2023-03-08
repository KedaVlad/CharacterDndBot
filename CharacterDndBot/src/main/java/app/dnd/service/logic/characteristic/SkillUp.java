package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.characteristics.SaveRoll;
import app.dnd.dto.characteristics.Skill;
import app.dnd.service.logic.proficiencies.ProficienciesAddPossession;

@Component
public class SkillUp {

	@Autowired
	private ProficienciesAddPossession proficienciesAddPossession;

	public void up(CharacterDnd character, Skill article) {

		if(article.getName().contains("SR")) {
			for(SaveRoll sr: character.getCharacteristics().getSaveRolls()) {
				if(sr.getName().equals(article.getName())) {
					sr.setProficiency(article.getProficiency());
					proficienciesAddPossession.add(character.getAbility().getProficiencies(), new Possession(sr.getName(), sr.getProficiency()));
					return;
				}
			}
		} else {
			for(Skill skill: character.getCharacteristics().getSkills()) {
				if(skill.getName().equals(article.getName())) {
					skill.setProficiency(article.getProficiency());
					proficienciesAddPossession.add(character.getAbility().getProficiencies(), new Possession(skill.getName(), skill.getProficiency()));
					return;
				}
			}
		}
	}
}
