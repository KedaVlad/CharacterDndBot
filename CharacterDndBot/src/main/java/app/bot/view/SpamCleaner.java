package app.bot.view;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import app.bot.event.CleanSpam;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class SpamCleaner {
	
	private final Bot bot;

	public SpamCleaner(Bot bot) {
		this.bot = bot;
	}

	@EventListener
	public void cleanSpam(CleanSpam cleanSpum) {
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