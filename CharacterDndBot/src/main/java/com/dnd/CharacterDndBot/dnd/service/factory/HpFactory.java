package com.dnd.CharacterDndBot.dnd.service.factory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.bot.model.act.Act;
import com.dnd.CharacterDndBot.bot.model.act.ReturnAct;
import com.dnd.CharacterDndBot.bot.model.act.SingleAct;
import com.dnd.CharacterDndBot.bot.model.act.actions.Action;
import com.dnd.CharacterDndBot.bot.model.user.User;
import com.dnd.CharacterDndBot.dnd.dto.CharacterDnd;
import com.dnd.CharacterDndBot.dnd.service.Executor;
import com.dnd.CharacterDndBot.dnd.service.Location;
import com.dnd.CharacterDndBot.dnd.service.gamer.Menu;
import com.dnd.CharacterDndBot.dnd.service.logic.hp.HpBuilderFactory;

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
	private HpBuilderFactory hpBuilderFactory;

	@Override
	public Act executeFor(Action action, User user) {	
		int stableHp = hpBuilderFactory.stable().buildBase(user.getCharactersPool().getActual());
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
	private HpBuilderFactory hpBuilderFactory;

	@Override
	public Act executeFor(Action action, User user) {	
		String answer = action.getAnswers()[0];
		CharacterDnd character = user.getCharactersPool().getActual();
		String text = "Congratulations, you are ready for adventure.";

		if (answer.contains("Stable")) {
			action = action.continueAction(hpBuilderFactory.stable().buildBase(character) + "");
		} else if(answer.contains("Random")) {
			action = action.continueAction(hpBuilderFactory.random().buildBase(character) + "");
		} else {
			Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
			Matcher matcher = pat.matcher(answer);
			int hp = 0;
			while (matcher.find()) {
				hp = ((Integer) Integer.parseInt(matcher.group()));
			}
			if (hp <= 0) {
				action = action.continueAction(hpBuilderFactory.stable().buildBase(character) + "");
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
		user.getCharactersPool().getActual().getHp().setMax((Integer) Integer.parseInt(action.getAnswers()[1]));
		user.getCharactersPool().save();
		return  menu.executeFor(Action.builder().build(), user);
	}
}




