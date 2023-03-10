package app.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.service.Executor;
import app.dnd.service.logic.hp.HpControler;
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

		if (text.matches("^(\\+|\\+\\+|-)\\d{1,3}")) {
			return hpTextComand.executeFor(action, user);
		} else if (text.matches("^(?i)exp\\d{1,6}")) {
			return expTextComand.executeFor(action, user);
		} else if(user.getActualHero().getCharacter() != null) {
			return memoirsTextComand.executeFor(action, user);
		} else {
			return SingleAct.builder().name("DeadEnd").text("Until you don`t have active Hero i can`t write memoirs about them... So, this text recognize OBLIVION!!!").build();
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
		int exp = (Integer) Integer.parseInt(action.getAnswers()[0].replaceAll("^(?i)exp(\\d+)", "$1"));
		if (lvlAddExperience.addExperience(user.getActualHero().getCharacter(), exp)) {

		}
		return menu.executeFor(action, user);
	}
}

@Component
class HpTextComand implements Executor<Action> {

	@Autowired
	private HpControler hpControler;
	@Autowired
	private Menu menu;

	@Override
	public Act executeFor(Action action, User user) {
		String num = action.getAnswers()[0].replaceAll("^(\\+\\+|\\+|-)(\\d+)", "$2");
		int value = (Integer) Integer.parseInt(num);
		if (action.getAnswers()[0].contains("++")) {
			hpControler.bonusHp(user.getActualHero().getCharacter(), value);
		} else if (action.getAnswers()[0].contains("+")) {
			hpControler.heal(user.getActualHero().getCharacter(), value);
		} else if (action.getAnswers()[0].contains("-")) {
			hpControler.damage(user.getActualHero().getCharacter(), value);
		} else {
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
		user.getActualHero().getCharacter().getMyMemoirs().add(action.getAnswers()[0]);
		return SingleAct.builder()
				.name("addMemoirs")
				.text("I will put it in your memoirs")
				.build();
	}
}

