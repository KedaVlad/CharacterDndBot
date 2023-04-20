package app.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.bot.model.UserCore;
import app.bot.service.UserCacheService;
import app.bot.service.UserCoreService;

@Controller
public class UserController<T extends UserCore> {

	@Autowired
	private UserCoreService<T> userService;
	@Autowired
	private UserCacheService<T> userCache;

	public T getById(Long id) {	
		
		if(userCache.isCached(id)) {
			return	userCache.get(id);
		}
		return userService.getById(id);
	}

	public void save(T user) {
		userService.save(user);
	}

}
