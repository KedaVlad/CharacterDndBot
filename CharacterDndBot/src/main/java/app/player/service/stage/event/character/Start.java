package app.player.service.stage.event.character;

import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.util.ButtonName;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.service.stage.Executor;
import app.user.model.User;

@Component
public class Start implements Executor<Action> {
	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(user.getId() + "")
				.act(SingleAct.builder()
						.name(ButtonName.START_B)
						.text("/characters - This command leads to your character library,"
								+ " where you can create and choose which character you play.\n"
								+"/text_commands - List of text commands available to you.")
						.build())
				.build();
	}
}
