package app.dnd.service.logic.characteristic.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.characteristics.Skill;

public interface SkillLogic {

	public void up(Skill skill, Long id);
	
}

@Component
class SkillFacade implements SkillLogic {
	
	@Autowired
	private SkillUp skillUp;

	@Override
	public void up(Skill skill, Long id) {
		skillUp.up(skill, id);
	}
	
}