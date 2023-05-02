package app.player.controller;

import app.player.model.event.ActEvent;
import app.player.model.event.EndGame;
import app.player.model.event.StageEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import app.player.model.EventHandler;
import app.player.service.stage.StageManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler("stageHandler")
public class StageHandler {

	private final StageManager stageManager;
	private final ApplicationEventPublisher eventPublisher;

	public StageHandler(StageManager stageManager, ApplicationEventPublisher eventPublisher) {
		this.stageManager = stageManager;
		this.eventPublisher = eventPublisher;
	}

	@EventListener
	public void handle(StageEvent event) {
		if(event.getTusk() != null) {
			eventPublisher.publishEvent(new ActEvent(this, event.getUser(),
					stageManager.find(event.getTusk().getLocation())
							.execute(event)));

		} else {
			eventPublisher.publishEvent(new EndGame(this, event.getUser()));
		}
	}
}
