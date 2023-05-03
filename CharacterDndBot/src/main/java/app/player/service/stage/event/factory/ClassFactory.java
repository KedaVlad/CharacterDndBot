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
@EventExecutor(Location.CLASS_FACTORY)
public class ClassFactory implements Executor {

	@Autowired
	private ClassFactoryExecutor classFactoryExecutor;

	@Override
	public Act execute(StageEvent event) {

		Action action = (Action) event.getTusk();
		switch (action.condition()) {
			case 0 -> {
				return classFactoryExecutor.chooseClass();
			}
			case 1 -> {
				return classFactoryExecutor.chooseSubClass(action);
			}
			case 2 -> {
				return classFactoryExecutor.chooseLvl(action);
			}
			case 3 -> {
				return classFactoryExecutor.apruve(action);
			}
			case 4 -> {
				return classFactoryExecutor.end(event.getUser(), action);
			}
		}
		log.error("ClassFactory: out of bounds condition");
		return null;
	}
}

@Component
class ClassFactoryExecutor  {

	@Autowired
	private DndFacade dndFacade;
	@Autowired
	private StatFactory statFactory;

	public Act chooseClass() {
		return ReturnAct.builder()
				.target(Button.START.NAME)
				.act(SingleAct.builder()
						.name("chooseClass")
						.stage(dndFacade.action().classes().chooseClass())
						.build())
				.build();
	}


	public Act chooseSubClass(Action action) {
		return SingleAct.builder()
				.name("ChooseClassArchtype")
				.stage(dndFacade.action().classes().chooseSubClass(action))
				.build();
	}

	public Act chooseLvl(Action action) {
		action.setText("What is your lvl?");
		return SingleAct.builder()
				.name("ChooseClassLvl")
				.mediator(true)
				.stage(action)
				.build();
	}

	public Act apruve(Action action) {
		return SingleAct.builder()
				.name("checkClassCondition")
				.stage(dndFacade.action().classes().apruve(action))
				.build();

	}

	public Act end(User user, Action action) {
		String className = action.getAnswers()[0];
		String archetype = action.getAnswers()[1];
		int lvl = Integer.parseInt(action.getAnswers()[2]);
		dndFacade.hero().classes().setClassDnd(user.getActualHero(), className, archetype, lvl);
		return statFactory.execute(new StageEvent (this, user, Action.builder().build()));
	}

}
