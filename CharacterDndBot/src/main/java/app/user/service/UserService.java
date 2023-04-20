package app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bot.service.UserCoreService;
import app.user.model.User;
@Service
public class UserService implements UserCoreService<User>{

	@Autowired
	private ScriptService scriptService;
	@Autowired
	private TrashService trashService;
	@Autowired
	private ActualHeroService actualHeroService;
	
	@Override
	public User getById(Long id) {
		User user = new User();
		user.setId(id);
		user.setTrash(trashService.getById(id));
		user.setScript(scriptService.getById(id));
		user.setActualHero(actualHeroService.getById(id));
		return user;
	}

	@Override
	public void save(User user) {
		scriptService.save(user.getScript());
		trashService.save(user.getTrash());
		actualHeroService.save(user.getActualHero());	
	}
}
