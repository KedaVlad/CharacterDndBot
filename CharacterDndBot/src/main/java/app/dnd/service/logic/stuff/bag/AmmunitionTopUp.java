package app.dnd.service.logic.stuff.bag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import app.dnd.dto.CharacterDnd;
import app.dnd.dto.stuffs.items.Ammunition;
import app.dnd.dto.stuffs.items.Items;

@Component
public class AmmunitionTopUp {

	public void topUp(CharacterDnd character, Ammunition ammunition, String valueInString) {
		
		int value = 0;
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(valueInString);
		while (matcher.find()) {
			value = ((Integer) Integer.parseInt(matcher.group()));
		}
		if (valueInString.contains("-"))
			value = value * -1;
		
		for(Items item: character.getStuff().getInsideBag()) {
			if((item instanceof Ammunition) && item.getName().equals(ammunition.getName())) {
				Ammunition ammo = (Ammunition) item;
				ammo.addValue(value);
				return;
			}
		}
	}
}
