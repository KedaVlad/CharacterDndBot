package app.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.dto.Refreshable.Time;
import app.dnd.service.Executor;
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

		return ReturnAct.builder()
				.target(MENU_B)
				.act(SingleAct.builder()
						.name("startRest")
						.action(Action.builder()
								.replyButtons()
								.buttons(new String[][] {{Time.LONG.toString(), Time.SHORT.toString()},{"RETURN TO MENU"}})
								.build())
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

		String timeInString = action.getAnswers()[0];
		if(timeInString.equals(Time.SHORT.toString())) {
			user.getCharactersPool().getActual().refresh(Time.SHORT);
			return SingleAct.builder()
					.name("EndRest")
					.text("Everything that depended on a short rest is reset.\n"
							+ "You have "+ user.getCharactersPool().getActual().getLvl().getLvl() +" Hit Dice available to restore your health.")
					.build();
		} else if(timeInString.equals(Time.LONG.toString())) { 
			user.getCharactersPool().getActual().refresh(Time.LONG);
			return SingleAct.builder()
					.name("EndRest")
					.text("You are fully rested and recovered!")
					.build();
		} else {			
			return null;
		}
	}
}
