package app.player.service.stage;

import app.player.event.UserEvent;
import app.player.model.Stage;
import app.player.model.act.Act;

public interface Executor {

	public abstract Act execute(UserEvent<Stage> event);
}
