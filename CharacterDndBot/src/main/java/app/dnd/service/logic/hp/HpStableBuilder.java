package app.dnd.service.logic.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ClassDnd;
import app.dnd.service.logic.characteristic.StatModificator;
import app.dnd.util.Convertor;

@Component
public class HpStableBuilder implements HpBuilder {

	@Autowired
	private StatModificator statModificator;
	private Convertor hp = x -> x / 2 + 1;
	
	@Override
	public int buildBase(CharacterDnd character) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int hp = this.hp.convert(character.getDndClass().get(0).getFirstHp());
		int start = character.getDndClass().get(0).getFirstHp() + modificator;

		for (int i = 1; i < character.getDndClass().get(0).getLvl(); i++) {
			if((hp + modificator) > 0) {
				start += hp + modificator;
			} else {
				start += 1;
			}			
		}
		return start;
	}
	
	@Override
	public int buildForLvlUp(CharacterDnd character, ClassDnd clazz) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int hp = this.hp.convert(clazz.getFirstHp());
		if((hp + modificator) > 0) {
			return hp + modificator;
		} else {
			return 1;
		}
	}

}