package com.dnd.CharacterDndBot.dnd.service.logic.stuff.bag;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.stuffs.Stuff;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Armor.ClassArmor;

@Component
public class ArmorRemove {

	public void remove(Stuff stuff, Armor armor) {
	
		int position = 0;
		if (armor.getType().getClazz().equals(ClassArmor.SHIELD)) {
			position = 1;
		} 
		stuff.getWeared()[position] = null;
	}
}
