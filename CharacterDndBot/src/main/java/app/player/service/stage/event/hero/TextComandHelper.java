package app.player.service.stage.event.hero;

import app.dnd.model.actions.Action;
import app.player.model.event.StageEvent;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.SingleAct;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;

@EventExecutor(Location.TEXT_COMMAND_HELPER)
public class TextComandHelper implements Executor {

	@Override
	public Act execute(StageEvent event) {
		
		return SingleAct.builder()
				.name("TextComandHelper")
				.stage(Action.builder()
						.text("""
								Text commands that are available to your hero, unless I ask you to write something specific at that moment:
								- To get damage, write "-" followed by the amount gained.
								- To get heal, write "+" followed by the amount gained.
								- To get bonus HP, write "++" followed by the amount gained.
								- To add experience, simply write "exp" followed by the amount gained.
								- To add a new memoir, just write it in the chat""")
						.build())
				.build();
	}

	
}
