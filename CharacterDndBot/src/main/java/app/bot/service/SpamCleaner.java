package app.bot.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
public class SpamCleaner {

	@Autowired
	private Bot bot;
	private ExecutorService distributor  = Executors.newSingleThreadExecutor(new ThreadFactory() {
		public Thread newThread(Runnable runnable) {
			Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			thread.setDaemon(true);
			return thread;
		}
	}); 

	public void run() {
		log.info("SpamCleaner run");
		distributor.execute(() -> {
			while (true) {
				try {
					cleanSpam(bot.getUpdateManager().getSpamConteiner().getSpammQueue().take());
				} catch (InterruptedException e) {
					log.error("Error in <SpamCleaner> thread: " + e.getMessage());
				}
			}
		});
	}

	private void cleanSpam(Update update) {
		if(update.hasMessage()) {	
			log.info("Clean spamm " + update.getMessage().getMessageId() + " ===========================================================");
			try {
				bot.execute(DeleteMessage.builder().chatId(update.getMessage().getChatId()).messageId(update.getMessage().getMessageId()).build());
			} catch (TelegramApiException e) {
				log.error(e.getMessage());
			}
		}
	}
}
