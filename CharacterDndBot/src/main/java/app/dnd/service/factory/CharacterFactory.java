package app.dnd.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.ActualHero;
import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.CharactersPool;
import app.bot.model.user.User;
import app.bot.service.ActualHeroService;
import app.bot.service.CharactersPoolService;
import app.dnd.dto.CharacterDnd;
import app.dnd.service.Executor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CharacterFactory implements Executor<Action> {

	@Autowired
	private CharacterStartCreate characterStartCreate;
	@Autowired
	private CharacterApruveName characterApruveName;
	@Autowired
	private CharacterFinishCreate characterFinishCreate;
	
	@Override
	public Act executeFor(Action action, User user) {
		switch (action.condition()) {
		case 1:
			return characterStartCreate.executeFor(action, user);
		case 2:
			return characterApruveName.executeFor(action, user);
		case 3:
			return characterFinishCreate.executeFor(action, user);
		}
		log.error("CharacterFactory: out of bounds condition");
		return null;
	}
}

@Component
class CharacterStartCreate implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		action.setMediator(true);
		return ReturnAct.builder()
				.act(SingleAct.builder()
						.name("CreateCharacter")
						.text("Traveler, how should I call you?!\n(Write Hero name)")
						.action(action)
						.build())
				.target(START_B)
				.build();
	}
}

@Component
class CharacterApruveName implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		action.setButtons(new String[][] {{ "Yeah, right" }});
		return SingleAct.builder()
				.name("apruveName")
				.text("So, can I call you - " + action.getAnswers()[1] + "? If not, repeat your name.")
				.action(action)
				.build();
	}
}

@Component
class CharacterFinishCreate implements Executor<Action> {

	@Autowired
	private RaceFactory raceFactory;
	@Autowired
	private ActualHeroService actualHeroService;
	@Autowired
	private CharactersPoolService characterPoolService;
	
	@Override
	public Act executeFor(Action action, User user) {
		ActualHero actualHero = actualHeroService.getById(user.getId());
		CharactersPool charactersPool = characterPoolService.getById(user.getId());
		actualHero.setCharacter(new CharacterDnd(action.getAnswers()[1]));
		charactersPool.getSavedCharacters().put(actualHero.getCharacter().getName(), actualHero.getCharacter());
		characterPoolService.save(charactersPool);
		actualHeroService.save(actualHero);
		return raceFactory.executeFor(Action.builder().build(), user);
	}
}
