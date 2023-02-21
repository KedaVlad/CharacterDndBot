package com.dnd.CharacterDndBot.dnd.service.logic.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.CharacterDnd;
import com.dnd.CharacterDndBot.dnd.dto.ClassDnd;
import com.dnd.CharacterDndBot.dnd.service.logic.characteristic.StatModificator;
import com.dnd.CharacterDndBot.dnd.util.math.Formalizer;

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
		int start = character.getDndClass().get(0).getFirstHp() + modificator;
		for (int i = 1; i < character.getDndClass().get(0).getLvl(); i++) {
			start += Formalizer.roll(character.getDndClass().get(0).getDiceHp()) + modificator;
		}
		return start; 
	}
}