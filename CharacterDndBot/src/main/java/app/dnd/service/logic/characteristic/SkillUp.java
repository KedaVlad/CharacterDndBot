package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.ActualHero;
import app.bot.service.ActualHeroService;
import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.characteristics.SaveRoll;
import app.dnd.dto.characteristics.Skill;
import app.dnd.service.logic.proficiencies.ProficienciesAddPossession;

@Component
public class SkillUp {

	@Autowired
	private ProficienciesAddPossession proficienciesAddPossession;
	@Autowired
	private ActualHeroService actualHeroService;

	public void up(Long id, Skill article) {

		ActualHero actualHero = actualHeroService.getById(id);
		if(article.getName().contains("SR")) {
			for(SaveRoll sr: actualHero.getCharacter().getCharacteristics().getSaveRolls()) {
				if(sr.getName().equals(article.getName())) {
					sr.setProficiency(article.getProficiency());
					proficienciesAddPossession.add(actualHero.getCharacter().getAbility().getProficiencies(), new Possession(sr.getName(), sr.getProficiency()));
					actualHeroService.save(actualHero);
					return;
				}
			}
		} else {
			for(Skill skill: actualHero.getCharacter().getCharacteristics().getSkills()) {
				if(skill.getName().equals(article.getName())) {
					skill.setProficiency(article.getProficiency());
					proficienciesAddPossession.add(actualHero.getCharacter().getAbility().getProficiencies(), new Possession(skill.getName(), skill.getProficiency()));
					actualHeroService.save(actualHero);
					return;
				}
			}
		}
	}
}
