package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.dnd.dto.CharacterDnd;
import app.dnd.dto.characteristics.SaveRoll;
import app.dnd.service.Location;
import app.dnd.util.ArrayToColumns;

@Component
public class SaveRollsButtonsBuilder {

	@Autowired
	private SkillSingleButtonBuilder skillSingleButtonBuilder;
	@Autowired
	private ArrayToColumns arrayToColumns;
	
	
	public BaseAction[][] build(CharacterDnd character) {
		
		SaveRoll[] saveRolls = character.getCharacteristics().getSaveRolls();
		BaseAction[] buttons = new BaseAction[saveRolls.length];
		
		for(int i = 0; i < saveRolls.length; i++) {
			buttons[i] = Action.builder()
					.name(skillSingleButtonBuilder.build(saveRolls[i], character))
					.objectDnd(saveRolls[i])
					.location(Location.CHARACTERISTIC)
					.build();
		}
		return arrayToColumns.rebuild(buttons, 2, BaseAction.class);
	}
}
