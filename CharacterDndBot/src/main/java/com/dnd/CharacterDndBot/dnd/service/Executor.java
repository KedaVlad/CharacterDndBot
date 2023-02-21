package com.dnd.CharacterDndBot.dnd.service;

import com.dnd.CharacterDndBot.bot.model.act.Act;
import com.dnd.CharacterDndBot.bot.model.act.actions.BaseAction;
import com.dnd.CharacterDndBot.bot.model.user.User;

public interface Executor<T extends BaseAction> extends ButtonName {

	public abstract Act executeFor(T action, User user);
}
