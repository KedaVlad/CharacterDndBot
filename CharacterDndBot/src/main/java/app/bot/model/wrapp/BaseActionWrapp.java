package app.bot.model.wrapp;

import app.bot.model.act.actions.BaseAction;
import app.bot.model.user.User;

public class BaseActionWrapp extends WrappForHandler<BaseAction> {
	public BaseActionWrapp(User user, BaseAction action) {
		super(user, action);
	}
}
