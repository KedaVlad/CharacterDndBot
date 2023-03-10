package app.dnd.service.character;

import org.springframework.stereotype.Component;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.service.Executor;

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
