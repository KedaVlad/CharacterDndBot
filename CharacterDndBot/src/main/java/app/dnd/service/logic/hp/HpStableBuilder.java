package app.dnd.service.logic.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ClassDnd;
import app.dnd.service.logic.characteristic.StatModificator;
import app.dnd.util.Convertor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HpStableBuilder implements HpBuilder {

	@Autowired
	private StatModificator statModificator;
	private Convertor hp = x -> x / 2 + 1;
	
	@Override
	public int buildBase(CharacterDnd character) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int hp = this.hp.convert(character.getDndClass().get(0).getFirstHp());
		log.info("HpStableBuilder (buildBase first): " + character.getDndClass().get(0).getFirstHp());
		int start = character.getDndClass().get(0).getFirstHp() + modificator;

		log.info("HpStableBuilder (buildBase start): " + start);
		for (int i = 1; i < character.getDndClass().get(0).getLvl(); i++) {
			start += hp + modificator;
		}
		log.info("HpStableBuilder (buildBase end): " + start);
		return start;
	}
	
	@Override
	public int buildForLvlUp(CharacterDnd character, ClassDnd clazz) {
		int modificator = statModificator.modificate(character.getCharacteristics().getStats()[2]) + character.getHp().getHpBonus();
		int hp = this.hp.convert(clazz.getFirstHp());
		return hp + modificator;
	}

}