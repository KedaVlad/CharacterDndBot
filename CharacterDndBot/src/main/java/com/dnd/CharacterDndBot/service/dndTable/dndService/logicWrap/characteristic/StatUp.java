package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat;

@Component
public class StatUp {

	public void up(Stat stat, int value) {
		stat.setValue(stat.getValue() + value);
		if (stat.getValue() > stat.getMaxValue()) {
			stat.setValue(stat.getMaxValue());
		}
	}

}
