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
import app.player.service.stage.event.hero.Menu;
import app.bot.model.user.ActualHero;
import app.bot.model.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.HP_FACTORY)
public class HpFactory implements Executor {

	@Autowired
	private HpFactoryExecutor hpFactoryExecutor;

	@Override
	public Act execute(StageEvent event) {
		Action action = (Action) event.getTusk();
		switch (action.condition()) {
			case 0 -> {
				return hpFactoryExecutor.startBuildHp(event.getUser().getActualHero());
			}
			case 1 -> {
				return hpFactoryExecutor.approveHp(event.getUser().getActualHero(), action);
			}
			case 3 -> {
				return hpFactoryExecutor.finishHp(event.getUser(), action);
			}
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

	public Act approveHp(ActualHero actualHero, Action action) {
		return SingleAct.builder()
				.name("approveHp")
				.stage(dndFacade.action().hp().apruveHp(actualHero, action))
				.build();
	}

	public Act finishHp(User user, Action action) {
		ActualHero actualHero = user.getActualHero();
		actualHero.setReadyToGame(true);
		dndFacade.hero().hp().grow(actualHero, Integer.parseInt(action.getAnswers()[1]));
		return  menu.execute(new StageEvent (this, user, Action.builder().build()));
	}

}





