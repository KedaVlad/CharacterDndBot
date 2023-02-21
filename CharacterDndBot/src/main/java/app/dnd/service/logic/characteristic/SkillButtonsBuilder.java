package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.Skill;

@Component
public class SkillButtonsBuilder {

	@Autowired
	private SkillSingleButtonBuilder skillSingleButtonBuilder;
	
	
	public String[] build(CharacterDnd character) {

		Skill[] skills = character.getCharacteristics().getSkills();
		String[] buttons = new String[skills.length];
		for(int i = 0; i < skills.length; i++) {
			buttons[i] = skillSingleButtonBuilder.build(skills[i], character);
		}
		return buttons;
	}
}
