package com.dnd.CharacterDndBot.dnd.service.logic.lvl;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.characteristics.Lvl;

@Component
public class LvlAddExperience {

	public boolean addExperience(Lvl lvl, int value) {
		lvl.setExperience(lvl.getExperience() + value);
		for (int i = lvl.getExpPerLvl().length - 1; i > 0; i--) {
			if (lvl.getExpPerLvl()[i] <= lvl.getExperience()) {
				if (lvl.getLvl() != i + 1) {
					lvl.setLvl(i + 1);
					return true;
				}
				return false;
			}
		}
		return false;
	}
}
