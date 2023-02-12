package com.dnd.CharacterDndBot.service.dndTable.dndService.userService;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;

@Component
public class Start implements Executor<Action> {
	@Override
	public Act executeFor(Action action, User user) {
		return ReturnAct.builder()
				.target(user.getId() + "")
				.act(SingleAct.builder()
						.name(START_B)
						.text("/characters - This command leads to your character library,"
								+ " where you can create and choose which character you play.\n")
						.build())
				.build();
	}
}
