package com.dnd.CharacterDndBot.service.bot;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.CharacterDndBot.datacontrol.UserFileService;
import com.dnd.CharacterDndBot.service.bot.user.ReadyToSend;
import com.dnd.CharacterDndBot.service.bot.user.User;
@Service
public class Moderator {

	@Autowired
	private UserFileService userFileService;
	@Autowired
	private Player player;


	private long id(Update update) {
		if (update.hasCallbackQuery()) {
			return update.getCallbackQuery().getMessage().getChatId();
		} else {
			return update.getMessage().getChatId();
		}
	}

	GameTable play(Update update) throws ClassNotFoundException, IOException {
			return new GameTable(userFileService.getUserById(id(update)),update);
	}

	class GameTable implements Callable<ReadyToSend>  {

		private final User user;
		private final Update update;

		public GameTable(User user, Update update) {
			this.user = user;
			this.update = update;
		}

		@Override
		public ReadyToSend call() throws Exception {
			synchronized (user) {
				ReadyToSend readyToSend = user.makeSend(player.playFor(update, user));
				userFileService.save(user);
				return readyToSend;
			}
		}
	}
}