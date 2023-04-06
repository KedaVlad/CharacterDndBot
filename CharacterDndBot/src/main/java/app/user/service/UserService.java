package app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.service.UserCoreService;
import app.user.model.User;

@Service
public class UserService implements UserCoreService<User>{

	@Autowired
	private ScriptService scriptService;
	@Autowired
	private CloudsService cloudsService;
	@Autowired
	private TrashService trashService;
	@Autowired
	private ActualHeroService actualHeroService;
	
	@Override
	public User getByUpdate(Update update) {
		User user = new User();
		Long id = idFindByUpdate(update);
		user.setId(id);
		user.setScript(scriptService.getById(id));
		user.setClouds(cloudsService.getById(id));
		user.setTrash(trashService.getById(id));
		user.setActualHero(actualHeroService.getById(id));
		return user;
	}
	
	private Long idFindByUpdate(Update update) {
		if(update.hasCallbackQuery()) {
			return update.getCallbackQuery().getMessage().getChatId();
		} else {
			return update.getMessage().getChatId();
		}
	}

	@Override
	public void save(User user) {
		cloudsService.save(user.getClouds());
		scriptService.save(user.getScript());
		trashService.save(user.getTrash());
		actualHeroService.save(user.getActualHero());	
	}
}
