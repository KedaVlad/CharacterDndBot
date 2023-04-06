package app.player.service.stage.event.character;

import org.springframework.stereotype.Service;

import app.dnd.model.actions.Action;
import app.player.model.act.Act;
import app.player.model.act.SingleAct;
import app.player.service.stage.Executor;
import app.user.model.User;

@Service
public class TextComandHelper implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		
		return SingleAct.builder()
				.name("TextComandHelper")
				.text("Text commands that are available to your hero, unless I ask you to write something specific at that moment:\n"
						+ "- To get damage, write \"-\" followed by the amount gained.\n"
						+ "- To get heal, write \"+\" followed by the amount gained.\n"
						+ "- To get bonus HP, write \"++\" followed by the amount gained.\n"
						+ "- To add experience, simply write \"exp\" followed by the amount gained.\n"	
						+ "- To add a new memoir, just write it in the chat")
				.build();
	}
}
