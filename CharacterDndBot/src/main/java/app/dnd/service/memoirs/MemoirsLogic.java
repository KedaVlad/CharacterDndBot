package app.dnd.service.memoirs;

import app.bot.model.user.ActualHero;

public interface MemoirsLogic {

	String memoirsText(ActualHero actualHero);
	void addMemoirs(ActualHero actualHero, String text);
}
