package app.dnd.service.logic.stuff.bag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.ActualHero;
import app.bot.service.ActualHeroService;
import app.dnd.dto.stuffs.items.Ammunition;
import app.dnd.dto.stuffs.items.Items;

@Component
public class AmmunitionTopUp {

	@Autowired
	private ActualHeroService actualHeroService;
	
	public void topUp(Long id, Ammunition ammunition, String valueInString) {
		
		int value = 0;
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(valueInString);
		while (matcher.find()) {
			value = ((Integer) Integer.parseInt(matcher.group()));
		}
		if (valueInString.contains("-"))
			value = value * -1;
		
		ActualHero actualHero = actualHeroService.getById(id);
		for(Items item: actualHero.getCharacter().getStuff().getInsideBag()) {
			if((item instanceof Ammunition) && item.getName().equals(ammunition.getName())) {
				Ammunition ammo = (Ammunition) item;
				ammo.addValue(value);
				actualHeroService.save(actualHero);
				return;
			}
		}
	}
}
