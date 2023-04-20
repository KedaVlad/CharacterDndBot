package app.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.event.ChatUpdate;
import app.bot.event.EndGame;
import app.bot.event.StartGame;
import app.bot.service.Player;
import app.player.event.UserEvent;
import app.user.model.User;

@Service
public class GamePlayer implements Player<User> {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	@EventListener
	public void start(StartGame<User> startSession) {
		
		eventPublisher.publishEvent(new UserEvent<Update>(startSession.getUser(), startSession.getUpdate()));
	}

	@Override
	@EventListener
	public void end(EndGame endSession) {
		eventPublisher.publishEvent(new ChatUpdate(endSession.getUser()));
	}

}
