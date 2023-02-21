package com.dnd.CharacterDndBot.dnd.service.gamer;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.bot.model.act.Act;
import com.dnd.CharacterDndBot.bot.model.act.ArrayActs;
import com.dnd.CharacterDndBot.bot.model.act.ReturnAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;
import com.dnd.CharacterDndBot.bot.model.act.actions.Action;
import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.service.Executor;
import com.dnd.CharacterDndBot.dnd.service.Location;

@Component
public class CharacterCaseExecutor implements Executor<Action> {
	
	@Override
	public Act executeFor(Action action, User user) {
		
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
											.location(Location.DOWNLOAD_OR_DELETE)
											.buttons(buttons)
											.build())
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
