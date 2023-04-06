package app.bot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.UserCore;

public interface UserCoreService<T extends UserCore> {
	
	public T getByUpdate(Update update);
	public void save(T user);

}
