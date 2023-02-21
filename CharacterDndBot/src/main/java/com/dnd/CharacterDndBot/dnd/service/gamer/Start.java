package com.dnd.CharacterDndBot.dnd.service.gamer;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.bot.model.act.Act;
import com.dnd.CharacterDndBot.bot.model.act.ReturnAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;
import com.dnd.CharacterDndBot.bot.model.act.actions.Action;
import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.service.Executor;

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
