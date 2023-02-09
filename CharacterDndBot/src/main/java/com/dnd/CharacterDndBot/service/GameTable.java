package com.dnd.CharacterDndBot.service;

import java.util.concurrent.Callable;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.dnd.CharacterDndBot.service.acts.ActiveAct;
import com.dnd.CharacterDndBot.service.dndTable.dndService.ExecutorFactory;

public class GameTable implements Callable<ReadyToWork> {
	
	private final User user;
	private final Update update;

	public GameTable(User user, Update update) {
		this.user = user;
		this.update = update;
	}

	private ActiveAct execute() {
		ActiveAct act = InitializerFactory.build(ExecutorFactory.build(HandlerFactory.build(update)
						.handleFor(user))
						.executeFor(user))
				.initFor(user);
		return act;
	}

	@Override
	public ReadyToWork call() throws Exception {
		synchronized (user) {
			return user.makeWork(execute());
		}
	}
	
}