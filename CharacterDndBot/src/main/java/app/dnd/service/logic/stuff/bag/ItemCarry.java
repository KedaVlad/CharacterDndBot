package app.dnd.service.logic.stuff.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.hero.CharacterDnd;
import app.dnd.model.stuffs.items.Armor;
import app.dnd.model.stuffs.items.Items;

@Component
public class ItemCarry {
	
	@Autowired
	private ArmorWear armorWear;
	
	public void carry(CharacterDnd character, Items item) {
		item.setUsed(true);
		character.getStuff().getPrepeared().add(item);
		character.getStuff().getInsideBag().remove(item);
		if (item instanceof Armor) {
			armorWear.wear(character.getStuff(), (Armor) item);
		}
	}
}
