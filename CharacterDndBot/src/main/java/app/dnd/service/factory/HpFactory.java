package app.dnd.service.factory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.ActualHero;
import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.bot.service.ActualHeroService;
import app.dnd.dto.CharacterDnd;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.service.gamer.Menu;
import app.dnd.service.logic.hp.HpControler;
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
	@Autowired
	private ActualHeroService actualHeroService;
	
	@Override
	public Act executeFor(Action action, User user) {	
		int stableHp = hpControler.buildHp().stable().buildBase(actualHeroService.getById(user.getId()).getCharacter());
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
	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {	
		ActualHero actualHero = actualHeroService.getById(user.getId());
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
		actualHeroService.save(actualHero);
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
	@Autowired
	private ActualHeroService actualHeroService;

	@Override
	public Act executeFor(Action action, User user) {	
		ActualHero actualHero = actualHeroService.getById(user.getId());
		actualHero.getCharacter().getHp().setMax((Integer) Integer.parseInt(action.getAnswers()[1]));
		actualHero.getCharacter().getHp().setNow(actualHero.getCharacter().getHp().getMax());
		actualHeroService.save(actualHero);
		return  menu.executeFor(Action.builder().build(), user);
	}
}




