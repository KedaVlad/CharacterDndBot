package app.dnd.service.lvl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Lvl;
import app.user.model.ActualHero;
@Component
public class LvlFacade implements LvlLogic {

	@Autowired
	private LvlService lvlService;
	
	@Override
	public boolean addExperience(ActualHero hero, int value) {
		Lvl lvl = lvlService.findByIdAndOwnerName(hero.getId(), hero.getName());
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

	@Override
	public void setLvl(ActualHero actualHero, int lvlValue) {
		Lvl lvl = lvlService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		lvl.setLvl(lvlValue);
		lvl.setExperience(expPerLvl[lvlValue - 1]);
		lvlService.save(lvl);
	}
}
