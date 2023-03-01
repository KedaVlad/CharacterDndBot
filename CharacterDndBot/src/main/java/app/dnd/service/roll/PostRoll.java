package app.dnd.service.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.PreRoll;
import app.bot.model.user.User;
import app.dnd.service.ButtonName;
import app.dnd.util.math.Formula;

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
