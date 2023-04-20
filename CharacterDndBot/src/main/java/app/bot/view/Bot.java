package app.bot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class Bot extends TelegramLongPollingBot {

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	private final String botUsername;
	private final String botToken;

	@Autowired
	public Bot(@Qualifier("botName") String botUsername, @Qualifier("token") String botToken) {
		this.botUsername = botUsername;
		this.botToken = botToken;
	}

	@Override
	public void onUpdateReceived(Update update) {
		eventPublisher.publishEvent(update);
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

