package app.player.service.stage.event.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.model.Refreshable.Time;
import app.dnd.model.actions.Action;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.service.stage.Executor;
import app.user.model.ActualHero;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestExecutor implements Executor<Action> {

	@Autowired
	private StartRest startRest;
	@Autowired
	private EndRest endRest;

	@Override
	public Act executeFor(Action action, User user) {
		switch (action.condition()) {
		case 0:
			return startRest.executeFor(action, user);
		case 1:
			return endRest.executeFor(action, user);
		}
		log.error("RestMenu: out of bounds condition");
		return null;
	}
}

@Component
class StartRest implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

		action.setButtons(new String[][] {{Time.LONG.toString(), Time.SHORT.toString()},{RETURN_TO_MENU}});
		action.setReplyButtons(true);
		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name("startRest")
						.action(action)
						.text("You are resting... How many hours did you have time to rest?\n"
								+ "Long rest - if 8 or more.\n"
								+ "Short rest - if more than 1.5 and less than 8.")
						.build())
				.build();	
	}
}

@Component
class EndRest implements Executor<Action> {


	@Override
	public Act executeFor(Action action, User user) {

		ActualHero actualHero = user.getActualHero();
		String timeInString = action.getAnswers()[0];
		String text = null;
		if(timeInString.equals(Time.SHORT.toString())) {
			actualHero.getCharacter().refresh(Time.SHORT);
			text ="Everything that depended on a short rest is reset.\n"
					+ "You have "+ actualHero.getCharacter().getLvl().getLvl() +" Hit Dice available to restore your health.";
		} else { 
			actualHero.getCharacter().refresh(Time.LONG);
			text = "You are fully rested and recovered!";
		}
		return SingleAct.builder()
				.name("EndRest")
				.text(text)
				.build();


	}
}
