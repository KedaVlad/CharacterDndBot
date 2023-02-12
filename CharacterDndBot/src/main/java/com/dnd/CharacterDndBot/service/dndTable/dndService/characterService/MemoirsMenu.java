package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;

public class MemoirsMenu extends Executor<Action>{

	public MemoirsMenu(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		String text = "MY MEMOIRS\n";
		int i = 1;
		for(String string: user.getCharactersPool().getActual().getMyMemoirs()) {
			text += i + ". " + string + "\n";
			i++;
		}
		return ReturnAct.builder()
				.target(text)
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
