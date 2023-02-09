package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.Stuff;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Items;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Convertor;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor.Armors;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Armor.ClassArmor;

public class StuffLogic implements CharacterLogic{

	private Stuff stuff;
	private Stat[] stats;
	private boolean barbarian;
	@Override
	public void initialize(CharacterDnd character) {
		stuff = character.getStuff();
		stats = character.getCharacteristics().getStats();
	}

	public void carry(Items item) {
		item.setUsed(true);
		if (item instanceof Armor) {
			wear((Armor) item);
		} else {
			stuff.getPrepeared().add(item);
		}
	}

	private void wear(Armor armor) {
		if (armor.getType().getClazz().equals(ClassArmor.SHIELD)) {
			if (stuff.getWeared()[1] == null) {
				stuff.getWeared()[1] = armor;
				stuff.getPrepeared().add(armor);
			} else {
				stuff.getInsideBag().add(stuff.getWeared()[1]);
				stuff.getPrepeared().remove(stuff.getWeared()[1]);
				stuff.getWeared()[1] = armor;
			}
		} else {
			if (stuff.getWeared()[0] == null) {
				stuff.getWeared()[0] = armor;
				stuff.getPrepeared().add(armor);
			} else {
				stuff.getInsideBag().add(stuff.getWeared()[0]);
				stuff.getPrepeared().remove(stuff.getWeared()[0]);
				stuff.getWeared()[0] = armor;
			}
		}
	}

	public String getAC() {
		String answer = "AC:";
		Convertor statConvert = x -> (x - 10) / 2;
		if (stuff.getWeared()[0] == null) {
			if (barbarian) {
				answer += (10 + statConvert.convert(stats[2].getValue()) + statConvert.convert(stats[1].getValue()));
			} else {
				answer += (10 + statConvert.convert(stats[1].getValue()));
			}
		} else {
			Armors type = stuff.getWeared()[0].getType();
			int armor;
			if (type.getStatDependBuff() > type.getBaseArmor()) {
				armor = type.getBaseArmor() + statConvert.convert(stats[1].getValue());
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
}
