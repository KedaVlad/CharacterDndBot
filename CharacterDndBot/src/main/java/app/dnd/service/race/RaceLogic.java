package app.dnd.service.race;

import app.user.model.ActualHero;

public interface RaceLogic {

	boolean isReadyToGame(ActualHero actualHero);

	void setRace(ActualHero actualHero, String raceName, String subRace);

}
