package app.bot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.event.CleanSpum;


@Component
public class Controller {
	
	@Autowired
	private SessionStartController sessionController;
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@EventListener
	public void start(Update update){
		
			if(sessionController.isInGame(update)) {
				eventPublisher.publishEvent(new CleanSpum(update));			
			} else {
				this.sessionController.startSession(update);	
			}
	}
}
