package app.dnd.service.classes;

import app.user.model.ActualHero;

public interface ClassesLogic {

	boolean isReadyToGame(ActualHero actualHero);

	void setClassDnd(ActualHero actualHero, String className, String archetype, int lvl);

}
