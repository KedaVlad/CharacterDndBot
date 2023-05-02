package app.bot.telegramapi;

import app.bot.model.event.StartSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class Bot extends TelegramLongPollingBot {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	private final String botUsername;
	private final String botToken;

	public Bot(String botToken, String botUsername) {
		this.botToken = botToken;
		this.botUsername = botUsername;
	}

	@Override
	public void onUpdateReceived(Update update) {
		applicationEventPublisher.publishEvent(new StartSession(this, update));
	}
	@Override
	public String getBotToken() {
		return botToken;
	}
	@Override
	public String getBotUsername() {
		return botUsername;
	}
}

