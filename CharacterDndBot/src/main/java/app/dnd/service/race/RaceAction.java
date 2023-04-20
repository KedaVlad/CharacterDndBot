package app.dnd.service.race;

import app.dnd.model.actions.Action;
import app.player.model.Stage;

public interface RaceAction {

	Stage chooseRace();

	Stage chooseSubRace(Action action);

	Stage apruveRace(Action action);

}
