package app.dnd.service.logic.stuff.bag;

import org.springframework.stereotype.Component;

import app.dnd.dto.stuffs.Stuff;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Armor.ClassArmor;

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
