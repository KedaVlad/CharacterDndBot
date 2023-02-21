package com.dnd.CharacterDndBot.bot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.CharacterDndBot.bot.model.SessionConteiner;
import com.dnd.CharacterDndBot.bot.model.SpamConteiner;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
public class UpdateManager {

	@Autowired
	private IdManager idManager;
	@Autowired
	private SessionConteiner sessionConteiner;
	@Autowired
	private UserIdFinder userIdFinder; 
	@Autowired
	private SpamConteiner spamConteiner;

	public void addUpdate(Update update) {
		try {
			if(idManager.getInSession().contains(userIdFinder.byUpdate(update))) {
				log.info("UpdateManager (addUpdate): add spamm");
				spamConteiner.getSpammQueue().put(update);
			} else {
				log.info("UpdateManager (addUpdate): add update");
				idManager.getInSession().add(userIdFinder.byUpdate(update));
				sessionConteiner.getUpdateQueue().put(update);
			}
		} catch (InterruptedException e) {
			log.error("UpdateManager (addUpdate): " + e.getMessage());
		}
	}
}

@Component
class UserIdFinder {

	public Long byUpdate(Update update) {
		if (update.hasCallbackQuery()) {
			return update.getCallbackQuery().getMessage().getChatId();
		} else {
			return update.getMessage().getChatId();
		}
	}
}
