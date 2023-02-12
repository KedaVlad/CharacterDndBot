package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.SaveRoll;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.proficiencies.ProficienciesValueInitializer;

@Component
public class SaveRollsButtonsBuilder {

	@Autowired
	private StatModificator statModificator;
	@Autowired
	private ProficienciesValueInitializer proficienciesValueInitializer;
	
	public String[] build(CharacterDnd character) {
		
		Stat[] stats = character.getCharacteristics().getStats();
		SaveRoll[] saveRolls = character.getCharacteristics().getSaveRolls();
		String[] buttons = new String[saveRolls.length];
		
		for(int i = 0; i < saveRolls.length; i++)
		{
			for (Stat stat : stats) {
				if (stat.getName() == saveRolls[i].getDepends()) {
					buttons[i] = "" + statModificator.modificate(stat);
				}
				if (saveRolls[i].getProficiency() != null)
				{
					buttons[i] += "(" + proficienciesValueInitializer.getProf(character.getAbility().getProficiencies(), saveRolls[i].getProficiency()) + ")";
				}
				buttons[i] += " " + saveRolls[i].getName();
			}
		}
		return buttons;
	}
}
