package app.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.user.User;

@Service
public class UserService {

	@Autowired
	private ScriptService1 scriptService;
	@Autowired
	private CloudsService1 cloudsService;
	@Autowired
	private TrashService1 trashService;
	@Autowired
	private UserIdFinder userIdFinder;
	
	
	public User getByUpdate(Update update) {
		
		Long id = userIdFinder.byUpdate(update);
		User user = new User();
		user.setId(id);
		user.setScript(scriptService.getById(id));
		user.setClouds(cloudsService.getById(id));
		user.setTrash(trashService.getById(id));
		user.setUpdate(update);
		return user;
	}
}
