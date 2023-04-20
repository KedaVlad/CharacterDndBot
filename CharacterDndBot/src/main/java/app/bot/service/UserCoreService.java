package app.bot.service;

import app.bot.model.UserCore;

public interface UserCoreService<T extends UserCore> {
	
	public T getById(Long id);
	public void save(T user);

}
