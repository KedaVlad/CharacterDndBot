package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ClassDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic.StatModificator;

@Component
public class HpRandomBuilder implements HpBuilder {

	@Autowired
	private StatModificator statModificator;
	
	@Override
	public int buildForLvlUp(CharacterDnd character, ClassDnd clazz) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		return Formalizer.roll(clazz.getDiceHp()) + modificator;
	}

	@Override
	public int buildBase(CharacterDnd character) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int start = character.getDndClass()[0].getFirstHp() + modificator;
		for (int i = 1; i < character.getDndClass()[0].getLvl(); i++) {
			start += Formalizer.roll(character.getDndClass()[0].getDiceHp()) + modificator;
		}
		return start; 
	}
}