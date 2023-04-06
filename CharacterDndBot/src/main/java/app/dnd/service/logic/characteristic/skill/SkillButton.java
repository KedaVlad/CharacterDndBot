package app.dnd.service.logic.characteristic.skill;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.Skill;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.Skills;
import app.dnd.service.logic.characteristic.CharacteristicsService;
import app.dnd.service.logic.talants.prof.ProficienciesService;
import app.dnd.util.ArrayToColumns;
import app.player.model.enums.Location;

public interface SkillButton {
	public BaseAction[][] menu(Long id);
	public String[][] targetChange(Skill skill);
}

@Component
class SkillButtonBuilder implements SkillButton {

	@Autowired
	private MenuSkillButtonBuilder menuSkillButtonBuilder;
	@Autowired
	private SkillSingleButtonBuilder skillSingleButtonBuilder;

	@Override
	public BaseAction[][] menu(Long id) {
		return menuSkillButtonBuilder.build(id);
	}


	@Override
	public String[][] targetChange(Skill skill) {
		return skillSingleButtonBuilder.build(skill);
	}
}

class MenuSkillButtonBuilder {

	@Autowired
	private CharacteristicsService characteristicsService;
	@Autowired
	private ArrayToColumns arrayToColumns;
	@Autowired
	private ProficienciesService proficienciesService;

	public BaseAction[][] build(Long id) {

		Characteristics characteristics = characteristicsService.getById(id); 
		Proficiencies proficiencies = proficienciesService.getById(id);
		Map<Skills, Skill> skills = characteristics.getSkills();
		BaseAction[] pool = new BaseAction[skills.keySet().size()];
		int i = 0;
		for(Skills skill: skills.keySet()) {
			pool[i] = Action.builder()
					.name((characteristics.modificator(skills.get(skill).getCore().getStat()) + proficiencies.getProfBonus(skills.get(skill).getProficiency())) + " " + skill.toString())
					.objectDnd(skills.get(skill))
					.location(Location.CHARACTERISTIC)
					.build();
			i++;
		}
		return arrayToColumns.rebuild(pool, 2, BaseAction.class);
	}
}

@Component
class SkillSingleButtonBuilder {

	public String[][] build(Skill skill) {

		if(skill.getProficiency() != null) {
			if(skill.getProficiency().equals(Proficiency.BASE)) {
				return new String[][] {{"Up to COMPETENSE"}};
			} else if(skill.getProficiency().equals(Proficiency.COMPETENSE)) {
				return null;
			} else {
				return new String[][] {{"Up to PROFICIENCY"}};
			}
		} else {
			return new String[][] {{"Up to PROFICIENCY"}};
		}
	}
}



