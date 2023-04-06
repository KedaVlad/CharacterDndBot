package app.player.service.stage.event.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.model.actions.Action;
import app.dnd.model.hero.CharacterDnd;
import app.dnd.service.characterpool.CharactersPoolService;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.service.stage.Executor;
import app.user.model.ActualHero;
import app.user.model.CharactersPool;
import app.user.model.User;
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
	private CharactersPoolService characterPoolService;
	
	@Override
	public Act executeFor(Action action, User user) {
		ActualHero actualHero = user.getActualHero();
		CharactersPool charactersPool = characterPoolService.getById(user.getId());
		actualHero.setCharacter(new CharacterDnd(action.getAnswers()[1]));
		charactersPool.getSavedCharacters().put(actualHero.getCharacter().getName(), actualHero.getCharacter());
		characterPoolService.save(charactersPool);
		return raceFactory.executeFor(Action.builder().build(), user);
	}
}
