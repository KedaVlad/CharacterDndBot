package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.Stat;

@Component
public class StatButtonsBuilder {

	@Autowired
	private StatModificator statModificator;

	public String[] build(CharacterDnd character) {

		Stat[] stats = character.getCharacteristics().getStats();
		String[] buttons = new String[stats.length];
		for(int i = 0; i < stats.length; i++) {
			buttons[i] = stats[i].getValue() + "["+ statModificator.modificate(stats[i]) + "] " + stats[i].getName();
		}
		return buttons;
	}

}
