package app.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import app.bot.controller.SpamDistributor;
import app.bot.view.Bot;
import app.user.model.User;
import app.bot.controller.EndSessionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BotInitializer {
	@Autowired
	private Bot myLongPollingBot;
	@Autowired
	private SpamDistributor<User> spamController;
	@Autowired
	private EndSessionController<User> endSessionController;

	@EventListener({ ContextRefreshedEvent.class })
	public void init() throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		try {
			telegramBotsApi.registerBot(myLongPollingBot);
		} catch (TelegramApiException e) {
			log.error("Error occured: " + e.getMessage());
		}
		endSessionController.run();
		spamController.run();
	}

}
