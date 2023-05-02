package app.player.service.stage.event.hero;

import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.Refreshable.Time;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.REST)
public class RestManager implements Executor {

	@Autowired
	private RestExecutor restExecutor;

	@Override
	public Act execute(StageEvent event) {
		switch (((Action) event.getTusk()).condition()) {
			case 0 -> {
				return restExecutor.startRest((Action) event.getTusk());
			}
			case 1 -> {
				return restExecutor.endRest(event.getUser().getActualHero(), (Action) event.getTusk());
			}
		}
		log.error("RestMenu: out of bounds condition");
		return null;
	}

}

@Component
class RestExecutor {

	@Autowired
	private DndFacade dndFacade;
	
	public Act startRest(Action action) {

		action.setButtons(new String[][] {{Time.LONG.toString(), Time.SHORT.toString()},{Button.RETURN_TO_MENU.NAME}});
		action.setText("""
				You are resting... How many hours did you have time to rest?
				Long rest - if 8 or more.
				Short rest - if more than 1.5 and less than 8.""");
		
		return ReturnAct.builder()
				.target(Button.MENU.NAME)
				.act(SingleAct.builder()
						.reply(true)
						.name("startRest")
						.stage(action)
						.build())
				.build();	
	}

	public Act endRest(ActualHero actualHero, Action action) {
		String timeInString = action.getAnswers()[0];
		if(timeInString.equals(Time.SHORT.toString())) {
			dndFacade.hero().refresh(actualHero, Time.SHORT);
			action.setText("Everything that depended on a short rest is reset.\n"
					+ "Also, You have Hit Dice available to restore your health.");
		} else { 
			dndFacade.hero().refresh(actualHero, Time.LONG);
			action.setText("You are fully rested and recovered!");
		}
		return SingleAct.builder()
				.name("EndRest")
				.stage(action)
				.build();
	}
	
	
	
}
