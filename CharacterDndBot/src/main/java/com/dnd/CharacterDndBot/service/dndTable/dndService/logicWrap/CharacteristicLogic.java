package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Characteristics;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat.Stats;

public class CharacteristicLogic implements CharacterLogic {

	private Characteristics characteristic;
	@Override
	public void initialize(CharacterDnd character) {
		characteristic = character.getCharacteristics();
	}
	
	public void setup(int str, int dex, int con, int intl, int wis, int cha)
	{
		up(0, str);
		up(1, dex);
		up(2, con);
		up(3, intl);
		up(4, wis);
		up(5, cha);
	}
	
	public void up(int stat, int value) {
		Stat target = characteristic.getStats()[stat];
		target.setValue(target.getValue() + value);
		if (target.getValue() > target.getMaxValue()) {
			target.setValue(target.getMaxValue());
		}
	}
	
	public void up(Stat stat, int value) {
		stat.setValue(stat.getValue() + value);
		if (stat.getValue() > stat.getMaxValue()) {
			stat.setValue(stat.getMaxValue());
		}
	}

	
}
