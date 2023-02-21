package app.dnd.service.gamer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.service.Executor;
import app.dnd.service.logic.hp.HpDamage;
import app.dnd.service.logic.hp.HpHeal;
import app.dnd.service.logic.lvl.LvlAddExperience;

@Service
public class TextComandExecutor implements Executor<Action> {

	@Autowired
	private ExpTextComand expTextComand;
	@Autowired
	private HpTextComand hpTextComand;
	@Autowired
	private MemoirsTextComand memoirsTextComand;

	@Override
	public Act executeFor(Action action, User user) {

		String text = action.getAnswers()[0];

		if (text.matches("^\\+\\d+")) {
			return expTextComand.executeFor(action, user);
		} else if (text.matches("^(hp|Hp|HP|hP)(\\+|-)\\d+")) {
			return hpTextComand.executeFor(action, user);
		} else {
			return memoirsTextComand.executeFor(action, user);
		}
	}
}


@Component
class ExpTextComand implements Executor<Action> {

	@Autowired
	private LvlAddExperience lvlAddExperience;
	@Autowired
	private Menu menu;

	@Override
	public Act executeFor(Action action, User user) {
		int exp = (Integer) Integer.parseInt(action.getAnswers()[0].replaceAll("^\\+(\\d+)", "$1"));
		if (lvlAddExperience.addExperience(user.getCharactersPool().getActual().getLvl(),exp)) {

		}
		return menu.executeFor(action, user);
	}
}

@Component
class HpTextComand implements Executor<Action> {

	@Autowired
	private HpDamage hpDamage;
	@Autowired
	private HpHeal hpHeal;
	@Autowired
	private Menu menu;

	@Override
	public Act executeFor(Action action, User user) {
		String num = action.getAnswers()[0].replaceAll("^(hp|Hp|HP|hP)(\\+|-)(\\d+)", "$3");
		int value = (Integer) Integer.parseInt(num);
		if (action.getAnswers()[0].contains("+")) {
			hpHeal.heal(user.getCharactersPool().getActual().getHp(), value);
		} else if (action.getAnswers()[0].contains("-")) {
			hpDamage.damaged(user.getCharactersPool().getActual().getHp(), value);
		} else
		{
			return SingleAct.builder()
					.name("MissHpFormula")
					.text("You make something vrong... I dont understand what do whith " + value + " heal(+) or damage(-)? Try again.")
					.build();
		}
		return menu.executeFor(action, user);
	}
}

@Component
class MemoirsTextComand implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		user.getCharactersPool().getActual().getMyMemoirs().add(action.getAnswers()[0]);
		return SingleAct.builder()
				.name("addMemoirs")
				.text("I will put it in your memoirs")
				.build();
	}
}

