package app.dnd.service.gamer;

import org.springframework.stereotype.Component;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.service.Executor;

@Component
public class Start implements Executor<Action> {
	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(user.getId() + "")
				.act(SingleAct.builder()
						.name(START_B)
						.text("/characters - This command leads to your character library,"
								+ " where you can create and choose which character you play.\n\n"
						+" Text commands that are available to your hero, unless I ask you to write something specific at that moment:\n"
								+ "- To add experience, simply write \"+\" followed by the amount gained.\n"	
								+ "- To get damage, write \"hp-\" followed by the amount gained.\n"
								+ "- To get heal, write \"hp+\" followed by the amount gained.\n"
								+ "- To get bonus HP, write \"hp++\" followed by the amount gained.\n"
								+ "- To add a new memoir, just write it in the chat")
						.build())
				.build();
	}
}
