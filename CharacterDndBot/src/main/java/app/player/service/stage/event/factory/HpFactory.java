package app.player.service.stage.event.factory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.model.actions.Action;
import app.dnd.model.hero.CharacterDnd;
import app.dnd.service.logic.hp.HpControler;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.player.service.stage.event.character.Menu;
import app.user.model.ActualHero;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HpFactory implements Executor<Action> {

	@Autowired
	private StartBuildHp startBuildHp;
	@Autowired
	private ApruveHp apruveHp;
	@Autowired
	private FinishHp finishHp;

	@Override
	public Act executeFor(Action action, User user) {
		switch (action.condition()) {
		case 0:
			return startBuildHp.executeFor(action, user);
		case 1:
			return apruveHp.executeFor(action, user);
		case 3:
			return finishHp.executeFor(action, user);
		}
		log.error("HpFactory: out of bounds condition");
		return null;
	}
}

@Component
class StartBuildHp implements Executor<Action> {

	@Autowired
	private HpControler hpControler;
	
	@Override
	public Act executeFor(Action action, User user) {	
		int stableHp = hpControler.buildHp().stable().buildBase(user.getActualHero().getCharacter());
		String[][] nextStep = { { "Stable " + stableHp, "Random ***" } };
		String text = "How much HP does your character have?\r\n" + "\r\n" + "You can choose stable or random HP count \r\n"
				+ "\r\n"
				+ "If you agreed with the game master on a different amount of HP, send its value. (Write the amount of HP)";

		return ReturnAct.builder()
				.target(START_B)
				.act(SingleAct.builder()
						.name("startBuildHp")
						.text(text)
						.action(Action.builder()
								.mediator()
								.buttons(nextStep)
								.location(Location.HP_FACTORY)
								.replyButtons()
								.build())
						.build())
				.build();
	}
}

@Component
class ApruveHp implements Executor<Action> {

	@Autowired
	private HpControler hpControler;

	@Override
	public Act executeFor(Action action, User user) {	
		ActualHero actualHero = user.getActualHero();
		String answer = action.getAnswers()[0];
		CharacterDnd character = actualHero.getCharacter();
		String text = "Congratulations, you are ready for adventure.";

		if (answer.contains("Stable")) {
			action = action.continueAction(hpControler.buildHp().stable().buildBase(character) + "");
		} else if(answer.contains("Random")) {
			action = action.continueAction(hpControler.buildHp().random().buildBase(character) + "");
		} else {
			Pattern pat = Pattern.compile("([0-9]{1,4})+?");
			Matcher matcher = pat.matcher(answer);
			int hp = 0;
			while (matcher.find()) {
				hp = ((Integer) Integer.parseInt(matcher.group()));
			}
			if (hp <= 0) {
				action = action.continueAction(hpControler.buildHp().stable().buildBase(character) + "");
				text = "Nice try... I see U very smart, but you will get stable " + hp
						+ " HP. You are ready for adventure.";
			} else {
				action = action.continueAction(hp + "");
			}
		}
		action.setButtons(new String[][] { { "Let's go" } });
		return SingleAct.builder()
				.name("apruveHp")
				.text(text)
				.action(action)
				.build();
	}
}

@Component
class FinishHp implements Executor<Action> {

	@Autowired
	private Menu menu;

	@Override
	public Act executeFor(Action action, User user) {	
		ActualHero actualHero = user.getActualHero();
		actualHero.getCharacter().getHp().setMax((Integer) Integer.parseInt(action.getAnswers()[1]));
		actualHero.getCharacter().getHp().setNow(actualHero.getCharacter().getHp().getMax());
		return  menu.executeFor(Action.builder().build(), user);
	}
}




