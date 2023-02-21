package com.dnd.CharacterDndBot.dnd.service.character;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.bot.model.act.Act;
import com.dnd.CharacterDndBot.bot.model.act.ReturnAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;
import com.dnd.CharacterDndBot.bot.model.act.actions.Action;
import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.service.Executor;

@Component
public class MemoirsExecutor implements Executor<Action>{

	@Override
	public Act executeFor(Action action, User user) {
		String text = "MY MEMOIRS\n";
		int i = 1;
		for(String string: user.getCharactersPool().getActual().getMyMemoirs()) {
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
