package app.bot.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bot.model.UserCore;
import app.bot.view.View;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EndSessionController<T extends UserCore> {
	
	@Autowired
	private View bot;
	@Autowired
	private SessionController<T> sessionController;

	private ExecutorService distributor = Executors.newSingleThreadExecutor(new ThreadFactory() {
		public Thread newThread(Runnable runnable) {
			Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			thread.setDaemon(true);
			return thread;
		}
	}); 
	
	public void compleat(T user) {
		bot.updateUser(user);
		sessionController.end(user);
	}
	
	
	public void run() {
		log.info("<EndSessionController> run");
		distributor.execute(() -> {
			while (true) {
						compleat(sessionController.getReadyToCompleat());
			}
		});
	}
}
