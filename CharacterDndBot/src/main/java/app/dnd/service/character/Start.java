package app.dnd.service.character;

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
								+ " where you can create and choose which character you play.\n"
								+"/text_comands - List of text commands available to you.")
						.build())
				.build();
	}
}
