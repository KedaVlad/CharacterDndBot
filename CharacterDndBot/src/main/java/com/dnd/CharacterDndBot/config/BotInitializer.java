package com.dnd.CharacterDndBot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.dnd.CharacterDndBot.service.bot.Bot;
import com.dnd.CharacterDndBot.service.bot.MessageSender;
import com.dnd.CharacterDndBot.service.bot.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BotInitializer {
	@Autowired
	Bot bot;
	@Autowired
	Session session;
	@Autowired
	MessageSender messageSender;

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
	}

}