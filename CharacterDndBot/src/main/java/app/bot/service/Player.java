package app.bot.service;

import app.bot.model.UserCore;

public interface Player<T extends UserCore> {

	public T playFor(T user);
}
