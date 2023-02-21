package app.dnd.service.logic.stuff.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.stuffs.Stuff;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Armor.ClassArmor;

@Component
public class ArmorWear {

	@Autowired
	private CarringRemove carringRemove;

	public void wear(Stuff stuff, Armor armor) {

		int position = 0;
		if (armor.getType().getClazz().equals(ClassArmor.SHIELD)) {
			position = 1;
		}
		if (stuff.getWeared()[position] != null) {
			carringRemove.remove(stuff, armor);
		}
		stuff.getWeared()[position] = armor;
	}
}
