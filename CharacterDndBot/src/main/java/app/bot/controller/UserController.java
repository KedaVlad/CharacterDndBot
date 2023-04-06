package app.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.UserCore;
import app.bot.service.UserCoreService;


@Controller
public class UserController<T extends UserCore> {

	@Autowired
	private UserCoreService<T> userService;
	
	public T getByUpdate(Update update) {
		return userService.getByUpdate(update);
	}
	
	public void save(T user) {
		userService.save(user);
	}
	
}
