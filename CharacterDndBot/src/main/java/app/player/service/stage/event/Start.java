package app.player.service.stage.event;

import app.dnd.model.actions.Action;
import app.player.model.event.StageEvent;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;

@EventExecutor(Location.START)
public class Start implements Executor {

	@Override
	public Act execute(StageEvent event) {
		event.getUser().reset();
		return ReturnAct.builder()
				.target(String.valueOf(event.getUser().getId()))
				.act(SingleAct.builder()
						.name(Button.START.NAME)
						.stage(Action.builder()
								.text("/characters - This command leads to your character library,"
										+ " where you can create and choose which character you play.\n"
										+"/text_commands - List of text commands available to you.")
								.build())
						.build())
				.build();
	}

}
