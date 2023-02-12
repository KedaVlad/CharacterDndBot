package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ClassDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Convertor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;
import com.dnd.CharacterDndBot.service.dndTable.dndService.userService.Menu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HpFactory extends Executor<Action> {
	public HpFactory(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		int condition = 0;
		if (action.getAnswers() != null) condition = action.getAnswers().length;
		switch (condition) {
		case 0:
			return startBuildHp(user);
		case 1:
			return apruveHp(user);
		case 3:
			return finish(user);
		}
		log.error("HpFactory: out of bounds condition");
		return null;
	}

	private Act startBuildHp(User user) {
		int stableHp = HpBuilderFactory.stable().buildBaseFor(user.getCharactersPool().getActual());
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

	private Act apruveHp(User user) {

		String answer = action.getAnswers()[0];
		CharacterDnd character = user.getCharactersPool().getActual();
		String text = "Congratulations, you are ready for adventure.";

		if (answer.contains("Stable")) {
			action = action.continueAction(HpBuilderFactory.stable().buildBaseFor(character) + "");
		} else if(answer.contains("Random")) {
			action = action.continueAction(HpBuilderFactory.random().buildBaseFor(character) + "");
		} else {
			Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
			Matcher matcher = pat.matcher(answer);
			int hp = 0;
			while (matcher.find()) {
				hp = ((Integer) Integer.parseInt(matcher.group()));
			}
			if (hp <= 0) {
				action = action.continueAction(HpBuilderFactory.stable().buildBaseFor(character) + "");
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

	private Act finish(User user) {
		user.getCharactersPool().getActual().getHp().setMax((Integer) Integer.parseInt(action.getAnswers()[2]));
		return new Menu(Action.builder().build()).executeFor(user);
	}
}


abstract class HpBuilderFactory {

	public static StableHpBuilder stable() {
		return new StableHpBuilder();
	}

	public static RandomHpBuilder random() {
		return new RandomHpBuilder();
	}
}

abstract class HpBuilder {
	public abstract int buildFor(CharacterDnd character, ClassDnd clazz);
	public abstract int buildBaseFor(CharacterDnd character);
}

class StableHpBuilder extends HpBuilder {

	private Convertor hp = x -> x / 2 + 1;
	@Override
	public int buildFor(CharacterDnd character, ClassDnd clazz) {
		int modificator = Formalizer.modificator(character.getCharacteristics().getStats()[2].getValue())
				+ character.getHp().getHpDice().getBuff();
		int hp = this.hp.convert(clazz.getFirstHp());
		return hp + modificator;
	}
	@Override
	public int buildBaseFor(CharacterDnd character) {
		int modificator = Formalizer.modificator(character.getCharacteristics().getStats()[2].getValue())
				+ character.getHp().getHpDice().getBuff();
		int hp = this.hp.convert(character.getDndClass()[0].getFirstHp());
		int start = character.getDndClass()[0].getFirstHp() + modificator;

		for (int i = 1; i < character.getDndClass()[0].getLvl(); i++) {
			start += hp + modificator;
		}
		return start;
	}

}

class RandomHpBuilder extends HpBuilder {

	@Override
	public int buildFor(CharacterDnd character, ClassDnd clazz) {
		int modificator = Formalizer.modificator(character.getCharacteristics().getStats()[2].getValue());
		return Formalizer.roll(clazz.getDiceHp()) + modificator;
	}

	@Override
	public int buildBaseFor(CharacterDnd character) {
		int modificator = Formalizer.modificator(character.getCharacteristics().getStats()[2].getValue()) 
				+ character.getHp().getHpDice().getBuff();
		int start = character.getDndClass()[0].getFirstHp() + modificator;
		for (int i = 1; i < character.getDndClass()[0].getLvl(); i++) {
			start += Formalizer.roll(character.getDndClass()[0].getDiceHp()) + modificator;
		}
		return start; 
	}
}
