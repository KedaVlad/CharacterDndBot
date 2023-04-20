package app.dnd.service.classes;

import app.dnd.model.actions.Action;
import app.player.model.Stage;

public interface ClassAction {

	Stage chooseClass();

	Stage chooseSubClass(Action action);

	Stage apruve(Action action);

}
