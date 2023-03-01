package app.dnd.service.logic.stuff.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.ActualHero;
import app.bot.service.ActualHeroService;
import app.dnd.dto.stuffs.Stuff;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Items;
@Component
public class CarringRemove {

	@Autowired
	private ArmorRemove armorRemove;
	@Autowired
	private ActualHeroService actualHeroService;

	public void remove(Long id, Items item) {
		ActualHero actualHero = actualHeroService.getById(id);
		Stuff stuff = actualHero.getCharacter().getStuff();
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
			actualHeroService.save(actualHero);
		}
	}
}
