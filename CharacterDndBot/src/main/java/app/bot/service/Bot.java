package app.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.config.BotConfig;
import lombok.Getter;

@Component
public class Bot extends TelegramLongPollingBot {
	
	private final BotConfig botConfig;
	@Getter
	@Autowired
	private UpdateManager updateManager;
	
	@Autowired
	public Bot(BotConfig botConfig) {
		this.botConfig = botConfig;
	}

	@Override
	public void onUpdateReceived(Update update) {
		updateManager.addUpdate(update);
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

