package com.dnd.CharacterDndBot.service.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.CharacterDndBot.config.BotConfig;

import lombok.Getter;

@Component
public class Bot extends TelegramLongPollingBot {
	
	private final BotConfig botConfig;
	@Getter
	@Autowired
	Session session;
	
	@Autowired
	public Bot(BotConfig botConfig) {
		this.botConfig = botConfig;
	}

	@Override
	public void onUpdateReceived(Update update) {
		session.addUpdate(update);
	}

	@Override
	public String getBotUsername() {
		return botConfig.getBotName();
	}

	@Override
	public String getBotToken() {
		return botConfig.getToken();
	}

}

