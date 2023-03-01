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
	private ActualHeroService actualHeroService;
	
	public boolean hasReadyHeroById(Long id) {
		ActualHero hero = actualHeroService.getById(id);
		return hero.getCharacter() != null && hero.getCharacter().getHp().getMax() > 0;
	}
	
	public void save(Long id) {
		CharactersPool charactersPool = charactersPoolService.getById(id);
		ActualHero actualHero = actualHeroService.getById(id);
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
		ActualHero actualHero = actualHeroService.getById(user.getId());

		if (actualHero != null) {
			charactersPool.getSavedCharacters().put(actualHero.getCharacter().getName(), actualHero.getCharacter());
		}
		
		if (charactersPool.getSavedCharacters().containsKey(name)) {
			Clouds clouds = user.getClouds();
			Trash trash = user.getTrash();
			for(ActiveAct act: clouds.getCloudsWorked()) {
				trash.getCircle().addAll(act.getActCircle());
				act.getActCircle().clear();
			}
			clouds.getCloudsTarget().addAll(clouds.getCloudsWorked());
			clouds.getCloudsWorked().clear();
			charactersPool.getSavedCharacters().get(name).setClouds(clouds.getCloudsTarget());
			actualHero.setCharacter(charactersPool.getSavedCharacters().get(name));
			clouds.setCloudsTarget(actualHero.getCharacter().getClouds());
			actualHeroService.save(actualHero);
			charactersPoolService.save(charactersPool);
		} 
	}
	
}
