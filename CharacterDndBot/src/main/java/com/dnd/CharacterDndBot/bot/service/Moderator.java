package com.dnd.CharacterDndBot.bot.service;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.repository.UserFileService;
@Service
public class Moderator {

	@Autowired
	private UserFileService userFileService;
	@Autowired
	private Player player;
	@Autowired
	private UserIdFinder userIdFinder;

	GameTable play(Update update) throws ClassNotFoundException, IOException {
		return new GameTable(userFileService.getUserById(userIdFinder.byUpdate(update)),update);
	}

	class GameTable implements Callable<User>  {

		private final User user;
		private final Update update;

		public GameTable(User user, Update update) {
			this.user = user;
			this.update = update;
		}

		@Override
		public User call() throws Exception {
			return player.playFor(update, user);
		}
	}
}