package app.player.service.stage.event.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
@EventExecutor(Location.STAT_FACTORY)
public class StatFactory implements Executor{

	@Autowired
	private StatFactoryExecutor statFactoryExecutor;
	
	@Override
	public Act execute(UserEvent<Stage> event) {

		Action action = (Action) event.getTask();
		switch (action.condition()) {
		case 0:
			return statFactoryExecutor.startBuildStat(action);
		case 1:
			return statFactoryExecutor.checkCondition(action);
		case 2:
			return statFactoryExecutor.finishBuildStat(event.getUser(), action);
		}
		log.error("StatFactory: out of bounds condition");
		return null;
	}

}

@Component
class StatFactoryExecutor {

	@Autowired
	private DndFacade dndFacade;
	@Autowired
	private HpFactory hpFactory;

	public Act startBuildStat(Action action) {

		return ReturnAct.builder()
				.target(Button.START.NAME)
				.act(SingleAct.builder()
						.name("ChooseStat")
						.mediator(true)
						.stage(dndFacade.action().ability().stat().factoryStatBuild())
						.build())
				.build();
	}
	
	public Act checkCondition(Action action) {
		
			return SingleAct.builder()
					.name("apruveStats")
					.stage(dndFacade.action().ability().stat().checkStatCondition(action))
					.build();
		}
	
	public Act finishBuildStat(User user, Action action) {
		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile("([0-9]{1,2})+?");
		Matcher matcher = pat.matcher(action.getAnswers()[0]);
		while (matcher.find()) {
			stats.add((Integer) Integer.parseInt(matcher.group()));
		}
		dndFacade.hero().ability().stat().setup(user.getActualHero(),  stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
		return hpFactory.execute(new UserEvent<Stage> (user, Action.builder().build()));
	}
	
	
}


