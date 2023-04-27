package app.player.controller;

import app.player.event.UpdateEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import app.bot.event.ChatUpdate;
import app.player.event.EndGame;
import app.player.event.StartGame;

@Service
public class Player {

	private final ApplicationEventPublisher eventPublisher;
	public Player(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@EventListener
	public void start(StartGame startGame) {
		eventPublisher.publishEvent(new UpdateEvent(this, startGame.getUser(), startGame.getUpdate()));
	}

	@EventListener
	public void end(EndGame endGame) {
        eventPublisher.publishEvent(new ChatUpdate(this, endGame.getUser()));
    }

}
