package app.bot.service;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.user.User;

@Service
public class Moderator {

	@Autowired
	private Player player;
	@Autowired
	private UserService userService;

	GameTable play(Update update) throws ClassNotFoundException, IOException {
		return new GameTable(update);
	}

	class GameTable implements Callable<User>  {

		private final Update update;

		public GameTable(Update update) {
			this.update = update;
		}

		@Override
		public User call() throws Exception {
			return player.playFor(userService.getByUpdate(update));
		}
	}
}

