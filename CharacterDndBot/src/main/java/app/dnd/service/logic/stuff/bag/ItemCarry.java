package app.dnd.service.logic.stuff.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.ActualHero;
import app.bot.service.ActualHeroService;
import app.dnd.dto.stuffs.items.Armor;
import app.dnd.dto.stuffs.items.Items;

@Component
public class ItemCarry {
	
	@Autowired
	private ArmorWear armorWear;
	@Autowired
	private ActualHeroService actualHeroService;
	
	public void carry(Long id, Items item) {
		ActualHero actualHero = actualHeroService.getById(id);
		item.setUsed(true);
		actualHero.getCharacter().getStuff().getPrepeared().add(item);
		actualHero.getCharacter().getStuff().getInsideBag().remove(item);
		if (item instanceof Armor) {
			armorWear.wear(actualHero.getCharacter().getStuff(), (Armor) item);
		}
		actualHeroService.save(actualHero);
	}
}
