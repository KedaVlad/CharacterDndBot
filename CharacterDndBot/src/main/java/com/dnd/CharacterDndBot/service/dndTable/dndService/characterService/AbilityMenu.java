package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;

@Component
public class AbilityMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action t, User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
