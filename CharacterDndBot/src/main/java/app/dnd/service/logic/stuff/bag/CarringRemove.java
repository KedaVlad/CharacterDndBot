package app.dnd.service.logic.stuff.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.stuffs.Stuff;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Items;

@Component
public class CarringRemove {

	@Autowired
	private ArmorRemove armorRemove;

	public void remove(CharacterDnd character, Items item) {
		Stuff stuff = character.getStuff();
		Items targetItem = null;
		for(Items target: stuff.getPrepeared()) {
			if(target.getPrivateKey().equals(item.getPrivateKey())) {
				targetItem = target;
			}
		}

		if(targetItem != null) {
			targetItem.setUsed(false);
			stuff.getPrepeared().remove(targetItem);
			stuff.getInsideBag().add(targetItem);
			if (item instanceof Armor) {
				armorRemove.remove(stuff, (Armor) item);
			}
		}
	}
}
