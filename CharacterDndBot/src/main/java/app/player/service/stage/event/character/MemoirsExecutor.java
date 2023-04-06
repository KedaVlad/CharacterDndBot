package app.player.service.stage.event.character;

import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.service.stage.Executor;
import app.user.model.User;

@Component
public class MemoirsExecutor implements Executor<Action>{

	@Override
	public Act executeFor(Action action, User user) {
		String text = "MY MEMOIRS\n";
		int i = 1;
		for(String string: user.getActualHero().getCharacter().getMyMemoirs()) {
			text += i + ". " + string + "\n";
			i++;
		}
		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name(MEMOIRS_B)
						.text(text)
						.action(Action.builder()
								.buttons(new String[][]{{RETURN_TO_MENU}})
								.replyButtons()
								.build())
						.build())
				.build();
	}
}
