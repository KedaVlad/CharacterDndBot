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
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.CLASS_FACTORY)
public class ClassFactory implements Executor {

	@Autowired
	private ClassFactoryExecutor classFactoryExecutor;

	@Override
	public Act execute(UserEvent<Stage> event) {

		Action action = (Action) event.getTask();
		switch (action.condition()) {
		case 0:
			return classFactoryExecutor.chooseClass(action);
		case 1:
			return classFactoryExecutor.chooseSubClass(action);
		case 2:
			return classFactoryExecutor.chooseLvl(action);
		case 3:
			return classFactoryExecutor.apruve(action);
		case 4:
			return classFactoryExecutor.end(event.getUser(), action);
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

	public Act chooseClass(Action action) {
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
		int lvl = ((Integer) Integer.parseInt(action.getAnswers()[2]));
		dndFacade.hero().classes().setClassDnd(user.getActualHero(), className, archetype, lvl);
		return statFactory.execute(new UserEvent<Stage> (user, Action.builder().build()));
	}

}
