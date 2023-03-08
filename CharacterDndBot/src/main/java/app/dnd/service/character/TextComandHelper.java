package app.dnd.service.character;

import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.service.Executor;

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
