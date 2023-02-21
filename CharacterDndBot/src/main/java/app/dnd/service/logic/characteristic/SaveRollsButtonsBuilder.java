package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.SaveRoll;

@Component
public class SaveRollsButtonsBuilder {

	@Autowired
	private SkillSingleButtonBuilder skillSingleButtonBuilder;
	
	public String[] build(CharacterDnd character) {
		
		SaveRoll[] saveRolls = character.getCharacteristics().getSaveRolls();
		String[] buttons = new String[saveRolls.length];
		
		for(int i = 0; i < saveRolls.length; i++) {
			buttons[i] = skillSingleButtonBuilder.build(saveRolls[i], character);
		}
		return buttons;
	}
}
