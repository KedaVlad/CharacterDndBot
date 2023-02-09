package com.dnd.CharacterDndBot.service.dndTable.dndService.userService;

import com.dnd.CharacterDndBot.service.User;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;

public class StartExecutor extends Executor<Action> {
	public StartExecutor(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
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
