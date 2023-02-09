package com.dnd.CharacterDndBot.service.dndTable.dndService.userService;

import com.dnd.CharacterDndBot.service.User;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ArrayActs;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;

public class CharacterCaseExecutor extends Executor<Action> {
	public CharacterCaseExecutor(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		if (user.getCharactersPool().getSavedCharacters().size() != 0) {
			String[][] buttons = new String[user.getCharactersPool().getSavedCharacters().size()][1];
			int i = 0;
			for (String character : user.getCharactersPool().getSavedCharacters().keySet()) {
				buttons[i][0] = user.getCharactersPool().getSavedCharacters().get(character).getName();
				i++;
			}
			return ReturnAct.builder()
					.target(START_B)
					.act(ArrayActs.builder()
							.name(CHARACTER_CASE_B)
							.pool(SingleAct.builder()
									.text("Choose the Hero or " + CREATE_B + " new one.")
									.action(Action.builder()
											.location(Location.CHARACTER_FACTORY)
											.buttons(new String[][] { { CREATE_B } })
											.replyButtons()
											.build())
									.build(),
									SingleAct.builder()
									.text("Your Heroes")
									.action(Action.builder()
											.location(Location.DOWNLOAD)
											.buttons(buttons).build())
									.build())
							.build())
					.build();
		} else {
			return SingleAct.builder()
					.name(CHARACTER_CASE_B)
					.text("You don't have a Hero yet, my friend. But after you " + CREATE_B
							+ " them, you can find them here.")
					.action(Action.builder()
							.location(Location.CHARACTER_FACTORY)
							.buttons(new String[][] { { CREATE_B } })
							.replyButtons()
							.build())
					.build();
		}
	}
}
