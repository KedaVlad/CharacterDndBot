package app.bot.controller;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.event.CleanSpum;
import app.bot.event.EndSession;
import app.bot.event.StartGame;
import app.bot.model.UserCore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionController<T extends UserCore> {

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Autowired
	private UserController<T> userController;
	private final Map<Long, T> inSession = new ConcurrentHashMap<>();

	@EventListener
	public void start(Update update){
		Long id = keyByUpdate(update);
		if(inSession.containsKey(id)) {
			eventPublisher.publishEvent(new CleanSpum(update));			
			
		} else {
			T user = userController.getById(id);
			inSession.put(id, user);
			eventPublisher.publishEvent(new StartGame<T>(user, update));
		}
	}

	@EventListener
	public void end(EndSession endSession) {
		userController.save(inSession.get(endSession.getId()));
		inSession.remove(endSession.getId());
	}

	private Long keyByUpdate(Update update) {

		if(update.hasCallbackQuery()) {
			return update.getCallbackQuery().getMessage().getChatId();
		} else if(update.hasMessage()){
			return update.getMessage().getChatId();
		} else {
			log.error("Controller <keyByUpdate> : update doesn`t include processed type.");
			return 1L;
		}
	}

}
