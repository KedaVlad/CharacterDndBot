package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;

public class LogicConteiner<T extends CharacterLogic> {

	private T logic;
	
	public LogicConteiner(T logic) {
		this.logic = logic;
	}
	
	public T initialize(CharacterDnd character)
	{
		logic.initialize(character);
		return logic;
	}
}
