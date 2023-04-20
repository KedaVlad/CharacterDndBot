package app.player.service.stage.event.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.service.DndFacade;
import app.player.event.UserEvent;
import app.player.model.EventExecutor;
import app.player.model.Stage;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.user.model.ActualHero;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.RACE_FACTORY)
public class RaceFactory implements Executor {

	@Autowired
	private RaceFactoryExecutor raceFactoryExecutor;

	@Override
	public Act execute(UserEvent<Stage> event) {
		
		Action action = (Action) event.getTask();
		switch (action.condition()) {
		case 0:
			return raceFactoryExecutor.chooseRace(event.getUser().getActualHero(), action);
		case 1:
			return raceFactoryExecutor.chooseSubRace(event.getUser().getActualHero(), action);
		case 2:
			return raceFactoryExecutor.apruve(event.getUser().getActualHero(), action);
		case 3:
			return raceFactoryExecutor.end(event.getUser(), action);
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
	
	public Act chooseRace(ActualHero actualHero, Action action) {		
		return ReturnAct.builder()
				.target(Button.START.NAME)
				.act(SingleAct.builder()
						.name("CreateRace")
						.stage(dndFacade.action().race().chooseRace())
						.build())
				.build();
	}
	
	public Act chooseSubRace(ActualHero actualHero, Action action) {	
		return SingleAct.builder()
				.name("ChooseSubRace")
				.stage(dndFacade.action().race().chooseSubRace(action))
				.build();
	}

	public Act apruve(ActualHero actualHero, Action action) {
		return SingleAct.builder()
				.name("checkCondition")
				.stage(dndFacade.action().race().apruveRace(action))
				.build();
	}

	public Act end(User user, Action action) {
		String raceName = action.getAnswers()[0];
		String subRace = action.getAnswers()[1];
		dndFacade.hero().race().setRace(user.getActualHero(), raceName, subRace);
		return classFactory.execute(new UserEvent<Stage> (user, Action.builder().build()));
	}

}




