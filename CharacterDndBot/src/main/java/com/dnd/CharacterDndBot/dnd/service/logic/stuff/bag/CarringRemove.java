package com.dnd.CharacterDndBot.dnd.service.logic.stuff.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.dnd.dto.stuffs.Stuff;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Armor;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Items;
@Component
public class CarringRemove {

	@Autowired
	private ArmorRemove armorRemove;
	
	public void remove(Stuff stuff, Items item) {
		item.setUsed(false);
		stuff.getPrepeared().remove(item);
		stuff.getInsideBag().add(item);
		if (item instanceof Armor) {
			armorRemove.remove(stuff, (Armor) item);
		}
	}
}
