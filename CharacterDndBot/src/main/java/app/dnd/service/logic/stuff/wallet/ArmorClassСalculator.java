package app.dnd.service.logic.stuff.wallet;

import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.ClassDnd;
import app.dnd.dto.characteristics.Stat;
import app.dnd.dto.stuffs.Stuff;
import app.dnd.dto.stuffs.items.Armor.Armors;
import app.dnd.service.logic.characteristic.StatModificator;

@Component
public class ArmorClassСalculator {

	private StatModificator statModificator;
	
	public String getAC(CharacterDnd character) {
		
		String answer = "AC:";
		Stuff stuff = character.getStuff();
		Stat[] stats = character.getCharacteristics().getStats();
		if (stuff.getWeared()[0] == null) {
			if (barbarianCheck(character)) {
				answer += (10 + statModificator.modificate(stats[2]) + statModificator.modificate(stats[1]));
			} else {
				answer += (10 + statModificator.modificate(stats[2]));
			}
		} else {
			Armors type = stuff.getWeared()[0].getType();
			int armor;
			if (type.getStatDependBuff() > type.getBaseArmor()) {
				armor = type.getBaseArmor() + statModificator.modificate(stats[1]);
				if (armor > type.getStatDependBuff()) {
					answer += type.getStatDependBuff();
				} else {
					answer += armor;
				}
			} else {
				armor = type.getBaseArmor();
			}

			if (stuff.getWeared()[1] == null) {
				return answer;
			} else {
				answer += "(+" + stuff.getWeared()[1].getType().getBaseArmor() + ")";
			}
		}
		return answer;
	}

	private boolean barbarianCheck(CharacterDnd character) {
		for (ClassDnd clazz : character.getDndClass()) {
			if (clazz.getClassName().toLowerCase().equals("barbarian")) {
				return true;
			}
		}
		return false;
	}
}
