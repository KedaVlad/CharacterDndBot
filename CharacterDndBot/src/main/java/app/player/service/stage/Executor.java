package app.player.service.stage;

import app.player.event.StageEvent;
import app.player.model.act.Act;

public interface Executor {

	Act execute(StageEvent event);
}
