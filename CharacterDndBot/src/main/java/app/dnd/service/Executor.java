package app.dnd.service;

import app.bot.model.act.Act;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.user.User;

public interface Executor<T extends BaseAction> extends ButtonName {

	public abstract Act executeFor(T action, User user);
}
