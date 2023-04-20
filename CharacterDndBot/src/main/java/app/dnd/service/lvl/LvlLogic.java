package app.dnd.service.lvl;

import app.user.model.ActualHero;

public interface LvlLogic {

	public final int[] expPerLvl = { 0, 300, 900, 2700, 6500, 14000, 23000, 34000, 48000, 64000, 85000, 100000, 120000,
			140000, 165000, 195000, 225000, 265000, 305000, 355000 };

	boolean addExperience(ActualHero hero, int value);

	void setLvl(ActualHero actualHero, int lvl);
}
