package app.dnd.service.classes;

import app.bot.model.user.ActualHero;

public interface ClassesLogic {

	boolean isReadyToGame(ActualHero actualHero);

	void setClassDnd(ActualHero actualHero, String className, String archetype, int lvl);

}
