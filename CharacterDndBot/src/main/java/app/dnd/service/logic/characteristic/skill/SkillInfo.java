package app.dnd.service.logic.characteristic.skill;

import org.springframework.stereotype.Component;

import app.dnd.model.characteristics.Skill;

public interface SkillInfo {

	public String menu();
	public String target(Skill skill);
	

}

@Component
class SkillInformator implements SkillInfo {

	@Override
	public String menu() {
		return "Choose skill which you want to roll or change";
	}

	@Override
	public String target(Skill skill) {
		return skill.getCore().toString();
	}
	
}
