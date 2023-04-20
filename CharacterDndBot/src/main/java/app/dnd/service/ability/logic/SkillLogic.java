package app.dnd.service.ability.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.Skill;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.service.ability.AbilityService;
import app.dnd.service.talants.logic.ProficienciesLogic;
import app.user.model.ActualHero;

public interface SkillLogic {

	public void up(ActualHero hero, Skill skill);
	boolean maximum(Skill skill);
}

@Component
class SkillFacade implements SkillLogic {
	

	@Autowired
	private ProficienciesLogic proficienciesLogic;
	@Autowired
	private AbilityService characteristicsService;

	@Override
	public void up(ActualHero hero, Skill skill) {
		Ability characteristics = characteristicsService.findByIdAndOwnerName(hero.getId(), hero.getName());
		Skill target = characteristics.getSkills().get(skill.getCore());
		target.setProficiency(skill.getProficiency());
		proficienciesLogic.addPossession(hero, new Possession(skill.getCore().toString(), skill.getProficiency()));
		characteristicsService.save(characteristics);
	}

	@Override
	public boolean maximum(Skill skill) {
		if(skill.getProficiency() == Proficiency.COMPETENSE) {
			return true;
		} else {
			return false;
		}
	
	}
	
}