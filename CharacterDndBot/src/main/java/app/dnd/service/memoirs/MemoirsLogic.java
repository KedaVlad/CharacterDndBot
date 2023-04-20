package app.dnd.service.memoirs;

import app.user.model.ActualHero;

public interface MemoirsLogic {

	String memoirsText(ActualHero actualHero);
	void addMemoirs(ActualHero actualHero, String text);
}
