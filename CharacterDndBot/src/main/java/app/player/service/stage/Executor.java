package app.player.service.stage;

import app.player.model.event.StageEvent;
import app.player.model.act.Act;

public interface Executor {

	Act execute(StageEvent event);
}
