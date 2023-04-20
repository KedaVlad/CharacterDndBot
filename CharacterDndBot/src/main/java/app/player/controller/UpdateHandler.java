package app.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.player.event.UserEvent;
import app.player.model.EventHandler;
import app.player.model.Stage;
import app.player.service.Handler;
import app.player.service.update.CallbackHandler;
import app.player.service.update.MessageHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler("updateHandler")
public class UpdateHandler implements Handler<Update> {
	
	@Autowired
	private CallbackHandler callbackHandler;
	@Autowired
	private MessageHandler messageHandler;
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Override
	@EventListener
	public void handle(UserEvent<Update> event) {
		
		if (event.getTask().hasCallbackQuery()) {
			eventPublisher.publishEvent(new UserEvent<Stage>(event.getUser(),
					callbackHandler.handle(
							event.getTask().getCallbackQuery(), 
							event.getUser())));
			
		} else if (event.getTask().hasMessage()) {
			eventPublisher.publishEvent(new UserEvent<Stage>(event.getUser(),
					messageHandler.handle(
							event.getTask().getMessage(), 
							event.getUser())));
			
		} else {
			log.error("HandlerFactory: Unattended type update");
		}
	}
}