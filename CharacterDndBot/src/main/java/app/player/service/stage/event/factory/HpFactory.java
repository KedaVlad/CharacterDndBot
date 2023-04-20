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
import app.player.service.stage.event.hero.Menu;
import app.user.model.ActualHero;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.HP_FACTORY)
public class HpFactory implements Executor {

	@Autowired
	private HpFactoryExecutor hpFactoryExecutor;

	@Override
	public Act execute(UserEvent<Stage> event) {
		Action action = (Action) event.getTask();
		switch (action.condition()) {
		case 0:
			return hpFactoryExecutor.startBuildHp(event.getUser().getActualHero());
		case 1:
			return hpFactoryExecutor.apruveHp(event.getUser().getActualHero(), action);
		case 3:
			return hpFactoryExecutor.finishHp(event.getUser(), action);
		}
		log.error("HpFactory: out of bounds condition");
		return null;
	}

}

@Component
class HpFactoryExecutor {

	@Autowired
	private DndFacade dndFacade;
	@Autowired
	private Menu menu;
	
	public Act startBuildHp(ActualHero hero) {	
	
		return ReturnAct.builder()
				.target(Button.START.NAME)
				.act(SingleAct.builder()
						.name("startBuildHp")
						.mediator(true)
						.reply(true)
						.stage(dndFacade.action().hp().startBuildHp(hero))
						.build())
				.build();
	}

	public Act finishHp(User user, Action action) {
		ActualHero actualHero = user.getActualHero();
		dndFacade.hero().hp().grow(actualHero, (Integer) Integer.parseInt(action.getAnswers()[1]));
		return  menu.execute(new UserEvent<Stage> (user, Action.builder().build()));
	}

	public Act apruveHp(ActualHero actualHero, Action action) {
		actualHero.setReadyToGame(true);
		return SingleAct.builder()
				.name("apruveHp")
				.stage(dndFacade.action().hp().apruveHp(actualHero, action))
				.build();
	}
}





