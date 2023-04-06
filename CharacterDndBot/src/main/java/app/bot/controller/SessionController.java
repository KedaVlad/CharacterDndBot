package app.bot.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.conteiner.ReadyToSendConteiner;
import app.bot.conteiner.SessionConteiner;
import app.bot.model.UserCore;
import app.bot.service.Player;
import lombok.extern.slf4j.Slf4j;

@Component
public class SessionController<T extends UserCore> {

	@Autowired
	private SessionConteiner sessionConteiner;
	@Autowired
	private UserController<T> userController;
	@Autowired
	private Moderator<T> moderator;
	@Autowired
	
	
	public void start(Update update)  {
		T user = userController.getByUpdate(update);
		this.sessionConteiner.start(user);
		this.moderator.serve(user);
	}
	

	public void end(T user) {
		userController.save(user);
		sessionConteiner.end(user.getId());
	}
	

	public boolean isIn(Update update) {
		return sessionConteiner.isIn(keyByUpdate(update));
	}
	
	private Long keyByUpdate(Update update) {

		if(update.hasCallbackQuery()) {
			return update.getCallbackQuery().getMessage().getChatId();
		} else if(update.hasMessage()) {
			return update.getMessage().getChatId();
		} else {
			return 1L;
		}
	}


	public T getReadyToCompleat() {
			return moderator.endGame();
	}
}

@Slf4j
@Component
class Moderator<T extends UserCore> {
	@Autowired
	private Player<T> player;
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	@Autowired
	private ReadyToSendConteiner<T> readyToSend;
	
	public void serve(T user) {
		try {
			readyToSend.add(executor.submit(
					new Ticket(user))
					.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public T endGame() {
		try {
			return readyToSend.get();
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	
	class Ticket implements Callable<T>  {

		private final T user;

		private Ticket(T user) {
			this.user = user;
		}
		
		@Override
		public T call() throws Exception {
			return player.playFor(user);
		}
	}
}
