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
import app.bot.model.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.RACE_FACTORY)
public class RaceFactory implements Executor {

	@Autowired
	private RaceFactoryExecutor raceFactoryExecutor;

	@Override
	public Act execute(StageEvent event) {
		
		Action action = (Action) event.getTusk();
		switch (action.condition()) {
			case 0 -> {
				return raceFactoryExecutor.chooseRace();
			}
			case 1 -> {
				return raceFactoryExecutor.chooseSubRace(action);
			}
			case 2 -> {
				return raceFactoryExecutor.approve(action);
			}
			case 3 -> {
				return raceFactoryExecutor.end(event.getUser(), action);
			}
		}
		log.error("RaceFactory: out of bounds condition");
		return null;
	}

}

@Component
class RaceFactoryExecutor {

	@Autowired
	private DndFacade dndFacade;
	@Autowired
	private ClassFactory classFactory;
	
	public Act chooseRace() {
		return ReturnAct.builder()
				.target(Button.START.NAME)
				.act(SingleAct.builder()
						.name("CreateRace")
						.stage(dndFacade.action().race().chooseRace())
						.build())
				.build();
	}
	
	public Act chooseSubRace(Action action) {
		return SingleAct.builder()
				.name("ChooseSubRace")
				.stage(dndFacade.action().race().chooseSubRace(action))
				.build();
	}

	public Act approve(Action action) {
		return SingleAct.builder()
				.name("checkCondition")
				.stage(dndFacade.action().race().apruveRace(action))
				.build();
	}

	public Act end(User user, Action action) {
		String raceName = action.getAnswers()[0];
		String subRace = action.getAnswers()[1];
		dndFacade.hero().race().setRace(user.getActualHero(), raceName, subRace);
		return classFactory.execute(new StageEvent (this, user, Action.builder().build()));
	}

}




