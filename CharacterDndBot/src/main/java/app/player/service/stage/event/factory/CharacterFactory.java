package app.player.service.stage.event.factory;

import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.service.DndFacade;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.bot.model.user.ActualHero;
import app.bot.model.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.CHARACTER_FACTORY)
public class CharacterFactory implements Executor {

	@Autowired
	private CharacterFactoryExecutor characterFactoryExecutor;
	
	@Override
	public Act execute(StageEvent event) {
		Action action = (Action) event.getTusk();
		switch (action.condition()) {
			case 0 -> {
				return characterFactoryExecutor.start(event.getUser().getActualHero(), action);
			}
			case 1 -> {
				return characterFactoryExecutor.approveName(action);
			}
			case 2 -> {
				return characterFactoryExecutor.end(event.getUser(), action);
			}
		}
		log.error("CharacterFactory: out of bounds condition");
		return null;
	}

}

@Component
class CharacterFactoryExecutor {
	
	@Autowired
	private RaceFactory raceFactory;
	@Autowired
	private DndFacade dndFacade;
	
	public Act start(ActualHero hero, Action action) {
		hero.setReadyToGame(false);
		action.setText("Traveler, how should I call you?!\n(Write Hero name)");
		return ReturnAct.builder()
				.act(SingleAct.builder()
						.name("CreateCharacter")
						.mediator(true)
						.stage(action)
						.build())
				.target(Button.START.NAME)
				.build();
	}
	
	public Act approveName(Action action) {
		
		action.setButtons(new String[][] {{ "Yeah, right" }});
		action.setText(new StringBuilder().append("So, can I call you - ")
				.append(action.getAnswers()[0])
				.append("? If not, repeat your name.")
				.toString());
		
		return SingleAct.builder()
				.name("apruveName")
				.stage(action)
				.build();
	}
	
	public Act end(User user, Action action) {
		String name = action.getAnswers()[0];
		dndFacade.hero().download(user.getActualHero(), name);
		return raceFactory.execute(new StageEvent (this, user, Action.builder().build()));
	}
	
}

