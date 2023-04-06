package app.bot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.user.model.User;

@Component
public class SessionStartController {

	@Autowired
	private SessionController<User> sessionModerator;
	
	public boolean isInGame(Update update) {
		return sessionModerator.isIn(update);
	}
	
	public void startSession(Update update) {
		this.sessionModerator.start(update);
	}
	
}
