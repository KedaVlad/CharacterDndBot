package app.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import app.player.event.UserEvent;
import app.player.model.EventHandler;
import app.player.model.Stage;
import app.player.model.act.Act;
import app.player.service.Handler;
import app.player.service.stage.StageManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler("stageHandler")
public class StageHandler implements Handler<Stage> {

	@Autowired
	private StageManager stageManager;
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	@EventListener
	public void handle(UserEvent<Stage> event) {
		log.info("StageHandler catch");
		if(event.getTask() != null) {
			eventPublisher.publishEvent(new UserEvent<Act>(event.getUser(),
					stageManager.find(event.getTask().getLocation())
					.execute(event)));
			
		} else {
			log.error("StageHandler <handle> : task ");
		}
	}
}
