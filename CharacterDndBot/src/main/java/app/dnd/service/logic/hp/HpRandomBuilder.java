package app.dnd.service.logic.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ClassDnd;
import app.dnd.service.logic.characteristic.StatModificator;
import app.dnd.util.math.Formalizer;

@Component
public class HpRandomBuilder implements HpBuilder {

	@Autowired
	private StatModificator statModificator;
	
	@Override
	public int buildForLvlUp(CharacterDnd character, ClassDnd clazz) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int hp = Formalizer.roll(clazz.getDiceHp());
		if((hp + modificator) > 0) {
			return hp + modificator;
		} else {
			return 1;
		}
	}

	@Override
	public int buildBase(CharacterDnd character) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int hp;
		int start = character.getDndClass().get(0).getFirstHp() + modificator;
		for (int i = 1; i < character.getDndClass().get(0).getLvl(); i++) {
			hp = Formalizer.roll(character.getDndClass().get(0).getDiceHp()) + modificator;
			if(hp > 0) {
				start += hp;
			} else {
				start += 1;
			}
		}
		return start; 
	}
}