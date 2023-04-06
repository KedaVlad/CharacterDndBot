package app.dnd.service.logic.characteristic.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.proficiency.Possession;
import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.Skill;
import app.dnd.service.logic.characteristic.CharacteristicsService;
import app.dnd.service.logic.talants.prof.ProficienciesLogic;

@Component
class SkillUp {

	@Autowired
	private ProficienciesLogic proficienciesLogic;
	@Autowired
	private CharacteristicsService characteristicsService;

	public void up(Skill skill, Long id) {

		Characteristics characteristics = characteristicsService.getById(id);
		Skill target = characteristics.getSkills().get(skill.getCore());
		target.setProficiency(skill.getProficiency());
		proficienciesLogic.addPossession(id, new Possession(skill.getCore().toString(), skill.getProficiency()));
		characteristicsService.save(characteristics);
	}
}
