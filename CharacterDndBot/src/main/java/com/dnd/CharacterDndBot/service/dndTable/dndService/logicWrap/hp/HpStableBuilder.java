package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ClassDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Convertor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.characteristic.StatModificator;

@Component
public class HpStableBuilder implements HpBuilder {

	@Autowired
	private StatModificator statModificator;
	private Convertor hp = x -> x / 2 + 1;
	
	@Override
	public int buildBase(CharacterDnd character) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int hp = this.hp.convert(character.getDndClass()[0].getFirstHp());
		int start = character.getDndClass()[0].getFirstHp() + modificator;

		for (int i = 1; i < character.getDndClass()[0].getLvl(); i++) {
			start += hp + modificator;
		}
		return start;
	}
	
	@Override
	public int buildForLvlUp(CharacterDnd character, ClassDnd clazz) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int hp = this.hp.convert(clazz.getFirstHp());
		return hp + modificator;
	}

}