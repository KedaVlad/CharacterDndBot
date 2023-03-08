package app.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bot.model.ActualHero;
import app.bot.model.act.ActiveAct;
import app.bot.model.user.CharactersPool;
import app.bot.model.user.Clouds;
import app.bot.model.user.Trash;
import app.bot.model.user.User;

@Service
public class CharactersPoolControler {
	
	@Autowired
	private CharactersPoolService charactersPoolService;
	@Autowired
	private ActualHeroService1 actualHeroService;
	
	public void save(ActualHero actualHero) {
		
		CharactersPool charactersPool = charactersPoolService.getById(actualHero.getId());
		charactersPool.getSavedCharacters().put(actualHero.getCharacter().getName(), actualHero.getCharacter());
		charactersPoolService.save(charactersPool);
	}

	public void delete(Long id, String name) {
		
		CharactersPool charactersPool = charactersPoolService.getById(id);
		ActualHero actualHero = actualHeroService.getById(id);
		if(actualHero.getCharacter() != null && actualHero.getCharacter().getName().equals(name)) {
			actualHero.setCharacter(null);
			actualHeroService.save(actualHero);
		} 
		if(charactersPool.getSavedCharacters().containsKey(name)) {
			charactersPool.getSavedCharacters().remove(name);
			charactersPoolService.save(charactersPool);
		}
	}

	public void download(User user, String name) {

		CharactersPool charactersPool = charactersPoolService.getById(user.getId());
		ActualHero actualHero = user.getActualHero();
		Clouds clouds = user.getClouds();
		Trash trash = user.getTrash();

		if (actualHero.getCharacter() != null) {		
			for(ActiveAct act: clouds.getCloudsWorked()) {
				trash.getCircle().addAll(act.getActCircle());
				act.getActCircle().clear();
			}
			clouds.getCloudsTarget().addAll(clouds.getCloudsWorked());
			clouds.getCloudsWorked().clear();
			actualHero.getCharacter().setClouds(clouds.getCloudsTarget());
			charactersPool.getSavedCharacters().put(actualHero.getCharacter().getName(), actualHero.getCharacter());
		}
		
		if (charactersPool.getSavedCharacters().containsKey(name)) {
			actualHero.setCharacter(charactersPool.getSavedCharacters().get(name));
			clouds.setCloudsTarget(actualHero.getCharacter().getClouds());
			charactersPoolService.save(charactersPool);
		} 
	}
	
}
