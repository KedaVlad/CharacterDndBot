package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Skill;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.proficiencies.ProficienciesValueInitializer;

@Component
public class SkillButtonsBuilder {

	@Autowired
	private StatModificator statModificator;
	@Autowired
	private ProficienciesValueInitializer proficienciesValueInitializer;

	public String[] build(CharacterDnd character) {

		Stat[] stats = character.getCharacteristics().getStats();
		Skill[] skills = character.getCharacteristics().getSkills();

		String[] buttons = new String[skills.length];
		for(int i = 0; i < skills.length; i++)
		{
			for (Stat stat : stats) {
				if (stat.getName() == skills[i].getDepends()) {
					buttons[i] = "" + statModificator.modificate(stat);
				}
				if (skills[i].getProficiency() != null)
				{
					buttons[i] += "(" + proficienciesValueInitializer.getProf(character.getAbility().getProficiencies(), skills[i].getProficiency()) + ")";
				}
				buttons[i] += " " + skills[i].getName();
			}
		}
		return buttons;
	}
}
