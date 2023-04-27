package app.bot.view;

import app.bot.event.StartSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class Bot extends TelegramLongPollingBot {

	private final ApplicationEventPublisher applicationEventPublisher;
	private final String botUsername;
	private final String botToken;

	@Autowired
	public Bot(@Qualifier("botName") String botUsername, @Qualifier("token") String botToken, ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
		this.botUsername = botUsername;
		this.botToken = botToken;
	}

	@Override
	public void onUpdateReceived(Update update) {
		applicationEventPublisher.publishEvent(new StartSession(this, update));
	}

	@Override
	public String getBotUsername() {
		return botUsername;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}
	
}

