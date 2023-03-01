package app.dnd.service.logic.lvl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.ActualHero;
import app.bot.service.ActualHeroService;
import app.dnd.dto.characteristics.Lvl;
import lombok.Getter;

@Component
public class LvlAddExperience {

	@Autowired
	private ActualHeroService actualHeroService;
	@Getter
	private final int[] expPerLvl = { 0, 300, 900, 2700, 6500, 14000, 23000, 34000, 48000, 64000, 85000, 100000, 120000,
			140000, 165000, 195000, 225000, 265000, 305000, 355000 };

	
	public boolean addExperience(Long id, int value) {
		ActualHero actualHero = actualHeroService.getById(id);
		Lvl lvl = actualHero.getCharacter().getLvl();
		lvl.setExperience(lvl.getExperience() + value);
		for (int i = expPerLvl.length - 1; i > 0; i--) {
			if (expPerLvl[i] <= lvl.getExperience()) {
				if (lvl.getLvl() != i + 1) {
					lvl.setLvl(i + 1);
					actualHeroService.save(actualHero);
					return true;
				}
				actualHeroService.save(actualHero);
				return false;
			}
		}
		return false;
	}
}
