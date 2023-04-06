package app.player.service.stage.event.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PreRoll;
import app.dnd.util.ButtonName;
import app.dnd.util.math.Formula;
import app.user.model.User;

@Component
public class PostRoll {

	@Autowired
	private HeroRolleFormalizer heroRolleFormalizer;
	public Action executeFor(PreRoll action, User user) {
		String text = "BED BED BED";
		Formula formula = heroRolleFormalizer.buildFormula(action.getAction(), user);
		String status = action.getStatus();
		if (status.equals("ADVANTAGE")) {
			text = formula.execute(true);
		} else if (status.equals("DISADVANTAGE")) {
			text = formula.execute(false);
		} else if (status.equals("BASIC")) {
			text = formula.execute();
		}

		Action answer = Action.builder().build();
		if (formula.isNatural1()) {
			answer.setAnswers(new String[] { text, ButtonName.CRITICAL_MISS });
		} else if (formula.isNatural20()) {
			answer.setAnswers(new String[] { text, ButtonName.CRITICAL_HIT });
		} else {
			answer.setAnswers(new String[] { text });
		}
		return answer;
	}

}
