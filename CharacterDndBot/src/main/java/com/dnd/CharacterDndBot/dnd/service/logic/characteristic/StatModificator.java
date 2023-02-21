package com.dnd.CharacterDndBot.dnd.service.logic.characteristic;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.characteristics.Stat;

@Component
public class StatModificator {

	public int modificate(Stat stat)
	{
		return (stat.getValue() - 10) / 2;
	}
	
}
