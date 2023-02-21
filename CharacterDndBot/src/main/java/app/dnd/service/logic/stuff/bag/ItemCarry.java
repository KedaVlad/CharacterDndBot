package app.dnd.service.logic.stuff.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.dto.stuffs.Stuff;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Items;

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
