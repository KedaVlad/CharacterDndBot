package app.dnd.service.hp;

import app.bot.model.user.ActualHero;

public interface HpLogic {
	
	void grow(ActualHero actualHero, int value);
	
	void damage(ActualHero actualHero, int value);
	
	void heal(ActualHero actualHero, int value);
	
	void bonusHp(ActualHero actualHero, int value);

	void setUpRandom(ActualHero actualHero);

	void setUpStable(ActualHero actualHero);

	int buildValueStableHp(ActualHero hero);

	int buildValueRandomHp(ActualHero actualHero);

    boolean isReadyToGame(ActualHero actualHero);
}
