package app.bot.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.UserCore;
import app.bot.service.UserCache;
import app.bot.view.View;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CachCleaner<T extends UserCore> {

	@Autowired
	private View bot;
	@Autowired
	private UserCache<T> userCache;
	private ExecutorService distributor  = Executors.newSingleThreadExecutor(new ThreadFactory() {
		public Thread newThread(Runnable runnable) {
			Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			thread.setDaemon(true);
			return thread;
		}
	}); 





	public void run() {
		log.info("<SpamDistributor> run");
		distributor.execute(() -> {
			while (true) {
				bot.spam(spamController.get());
			}
		});
	}

}
