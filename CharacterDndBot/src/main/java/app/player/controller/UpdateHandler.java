package app.player.controller;

import app.player.model.event.StageEvent;
import app.player.model.event.UpdateEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import app.player.model.EventHandler;
import app.player.service.update.CallbackHandler;
import app.player.service.update.MessageHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler("updateHandler")
public class UpdateHandler {

	private final CallbackHandler callbackHandler;
	private final MessageHandler messageHandler;
	private final ApplicationEventPublisher eventPublisher;

	public UpdateHandler(CallbackHandler callbackHandler, MessageHandler messageHandler, ApplicationEventPublisher eventPublisher) {
		this.callbackHandler = callbackHandler;
		this.messageHandler = messageHandler;
		this.eventPublisher = eventPublisher;
	}


	@EventListener
	public void handle(UpdateEvent event) {

		if (event.getTusk().hasCallbackQuery()) {
			eventPublisher.publishEvent(new StageEvent(this, event.getUser(),
					callbackHandler.handle(
							event.getTusk().getCallbackQuery(),
							event.getUser())));

		} else if (event.getTusk().hasMessage()) {
			eventPublisher.publishEvent(new StageEvent(this, event.getUser(),
                    messageHandler.handle(
                            event.getTusk().getMessage(),
                            event.getUser())));

		} else {
			log.error("HandlerFactory: Unattended type update");
		}
	}
}