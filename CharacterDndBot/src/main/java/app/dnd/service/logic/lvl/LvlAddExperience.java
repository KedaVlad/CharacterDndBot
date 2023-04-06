package app.dnd.service.logic.lvl;

import org.springframework.stereotype.Component;

import app.dnd.model.characteristics.Lvl;
import app.dnd.model.hero.CharacterDnd;
import lombok.Getter;

@Component
public class LvlAddExperience {

	@Getter
	public static final int[] expPerLvl = { 0, 300, 900, 2700, 6500, 14000, 23000, 34000, 48000, 64000, 85000, 100000, 120000,
			140000, 165000, 195000, 225000, 265000, 305000, 355000 };

	
	public boolean addExperience(CharacterDnd character, int value) {
		Lvl lvl = character.getLvl();
		lvl.setExperience(lvl.getExperience() + value);
		for (int i = expPerLvl.length - 1; i > 0; i--) {
			if (expPerLvl[i] <= lvl.getExperience()) {
				if (lvl.getLvl() != i + 1) {
					lvl.setLvl(i + 1);
					return true;
				}
				return false;
			}
		}
		return false;
	}
}
