package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat;

@Component
public class StatModificator {

	public int modificate(Stat stat)
	{
		return (stat.getValue() - 10) / 2;
	}
	
}
