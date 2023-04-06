package app.player.service;

import app.bot.model.UserCore;

public interface Handler<T extends UserCore> {

	public T handle(T user);
}
