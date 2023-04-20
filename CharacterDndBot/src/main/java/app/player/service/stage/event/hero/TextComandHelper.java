package app.player.service.stage.event.hero;

import app.dnd.model.actions.Action;
import app.player.event.UserEvent;
import app.player.model.EventExecutor;
import app.player.model.Stage;
import app.player.model.act.Act;
import app.player.model.act.SingleAct;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;

@EventExecutor(Location.TEXT_COMAND_HELPER)
public class TextComandHelper implements Executor {

	@Override
	public Act execute(UserEvent<Stage> event) {
		
		return SingleAct.builder()
				.name("TextComandHelper")
				.stage(Action.builder()
						.text("Text commands that are available to your hero, unless I ask you to write something specific at that moment:\n"
								+ "- To get damage, write \"-\" followed by the amount gained.\n"
								+ "- To get heal, write \"+\" followed by the amount gained.\n"
								+ "- To get bonus HP, write \"++\" followed by the amount gained.\n"
								+ "- To add experience, simply write \"exp\" followed by the amount gained.\n"	
								+ "- To add a new memoir, just write it in the chat")
						.build())
				.build();
	}

	
}
