package com.dnd.CharacterDndBot.service.dndTable.dndService;

import com.dnd.CharacterDndBot.service.User;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;

public abstract class Executor<T extends BaseAction> implements ButtonName {

	protected T action;

	public Executor(T action) {
		this.action = action;
	}

	public abstract Act executeFor(User user);
}
