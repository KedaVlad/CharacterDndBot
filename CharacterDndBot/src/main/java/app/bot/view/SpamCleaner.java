package app.bot.view;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import app.bot.event.CleanSpum;
import lombok.extern.slf4j.Slf4j;

public interface SpamCleaner {

	public void cleanSpam(Update update, AbsSender absSender);
}

@Component
@Slf4j
class MySpamCleaner {
	
	
	private Bot bot;
	
	@EventListener
	public void cleanSpam(CleanSpum cleanSpum) {
		Update update = cleanSpum.getUpdate();
		if(update.hasMessage()) {	
			try {
				bot.execute(DeleteMessage.builder()
						.chatId(update.getMessage().getChatId())
						.messageId(update.getMessage().getMessageId())
						.build());
			} catch (TelegramApiException e) {
				log.error("MySpamCleaner: " + e.getMessage());
			}
		}
	}
}