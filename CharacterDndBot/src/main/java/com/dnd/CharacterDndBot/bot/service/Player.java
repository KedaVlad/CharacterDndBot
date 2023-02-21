package com.dnd.CharacterDndBot.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.service.ActionHandler;


@Service
public class Player { 
	
	@Autowired
	private UpdateHandler updateHandler;
	@Autowired
	private ActionHandler actionHandler;
	@Autowired
	private ActHandler actHandler;
	
	public User playFor(Update update, User user) {
		return actHandler.handle(actionHandler.handle(updateHandler.handle(update,user), user), user);
	}
}

