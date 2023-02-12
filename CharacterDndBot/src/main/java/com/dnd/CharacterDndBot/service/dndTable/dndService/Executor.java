package com.dnd.CharacterDndBot.service.dndTable.dndService;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.bot.user.User;

public interface Executor<T extends BaseAction> extends ButtonName {

	public abstract Act executeFor(T action, User user);
}
