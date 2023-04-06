package app.player.service.stage;

import app.bot.model.UserCore;

public interface Executor<T extends UserCore> {

	public abstract void executeFor(T user);
}
