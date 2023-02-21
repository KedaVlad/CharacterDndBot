package com.dnd.CharacterDndBot.dnd.service.logic.stuff.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.stuffs.Stuff;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Items;

@Component
public class ItemCarry {
	
	@Autowired
	private ArmorWear armorWear;
	
	public void carry(Stuff stuff, Items item) {
		item.setUsed(true);
		stuff.getPrepeared().add(item);
		stuff.getInsideBag().remove(item);
		if (item instanceof Armor) {
			armorWear.wear(stuff, (Armor) item);
		}
	}
}
