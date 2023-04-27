package app.bot.controller;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import app.bot.event.StartSession;
import app.bot.service.UserCacheService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.event.CleanSpam;
import app.bot.event.EndSession;
import app.player.event.StartGame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SessionController {

	private final ApplicationEventPublisher eventPublisher;
	private final UserCacheService userCacheService;
	private final List<Long> idInSession = new CopyOnWriteArrayList<>();

	public SessionController(ApplicationEventPublisher eventPublisher, UserCacheService userCacheService) {
		this.eventPublisher = eventPublisher;
		this.userCacheService = userCacheService;
	}

	@EventListener
	public void start(StartSession startSession){
		Long id = keyByUpdate(startSession.getUpdate());
		if(idInSession.contains(id)) {
			eventPublisher.publishEvent(new CleanSpam(this, startSession.getUpdate()));
		} else {
			idInSession.add(id);
			eventPublisher.publishEvent(new StartGame(this, userCacheService.getById(id), startSession.getUpdate()));
		}
	}

	@EventListener
	public void end(EndSession endSession) {
		idInSession.remove(endSession.getId());
	}

	private Long keyByUpdate(Update update) {

		if(update.hasCallbackQuery()) {
			return update.getCallbackQuery().getMessage().getChatId();
		} else if(update.hasMessage()){
			return update.getMessage().getChatId();
		} else {
			log.error("SessionController <keyByUpdate> : update does not include processed type.");
			return 1L;
		}
	}

}
