package app.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.user.User;

@Service
public class UserService {

	@Autowired
	private ScriptService scriptService;
	@Autowired
	private CloudsService cloudsService;
	@Autowired
	private TrashService trashService;
	@Autowired
	private UserIdFinder userIdFinder;
	@Autowired
	private ActualHeroService1 actualHeroService;
	
	
	public User getByUpdate(Update update) {
		
		Long id = userIdFinder.byUpdate(update);
		User user = new User();
		user.setId(id);
		user.setScript(scriptService.getById(id));
		user.setClouds(cloudsService.getById(id));
		user.setTrash(trashService.getById(id));
		user.setActualHero(actualHeroService.getById(id));
		user.setUpdate(update);
		return user;
	}
}
