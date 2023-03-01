package app.bot.model.wrapp;

import app.bot.model.act.Act;
import app.bot.model.user.User;

public class ActWrapp extends WrappForHandler<Act>{

	public ActWrapp(User user, Act target) {
		super(user, target);
	}
}
