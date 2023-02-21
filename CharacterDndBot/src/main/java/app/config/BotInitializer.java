package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import app.bot.model.Bot;
import app.bot.service.MessageSender;
import app.bot.service.Session;
import app.bot.service.SpamCleaner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BotInitializer {
	@Autowired
	private Bot bot;
	@Autowired
	private Session session;
	@Autowired
	private MessageSender messageSender;
	@Autowired
	private SpamCleaner spamCleaner;

	@EventListener({ ContextRefreshedEvent.class })
	public void init() throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		try {
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException e) {
			log.error("Error occured: " + e.getMessage());
		}
		session.run();
	    messageSender.run();
	    spamCleaner.run();
	}

}
