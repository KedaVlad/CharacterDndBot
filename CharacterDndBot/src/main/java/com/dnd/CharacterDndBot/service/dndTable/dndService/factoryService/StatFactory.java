package com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat.Stats;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Executor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.Location;
import com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap.LogicFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatFactory extends Executor<Action> {
	
	public StatFactory(Action action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		int condition = 0;
		if (action.getAnswers() != null) condition = action.getAnswers().length;
		switch (condition) {
		case 0:
			return startBuildStat();
		case 1:
			return apruveStats();
		case 2:
			return finish(user);
		}
		log.error("StatFactory: out of bounds condition");
		return null;
	}

	private Act startBuildStat() {
		String name = "ChooseStat";
		String godGift = Formalizer.randomStat() + ", " + Formalizer.randomStat() + ", " + Formalizer.randomStat()
				+ ", " + Formalizer.randomStat() + ", " + Formalizer.randomStat() + ", " + Formalizer.randomStat();

		String text = "Now let's see what you have in terms of characteristics.\r\n" + "\r\n"
				+ "Write the value of the characteristics in order: Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma.\r\n"
				+ "1.Each stat cannot be higher than 20.\r\n"
				+ "2. Write down stats without taking into account buffs from race / class.\r\n" + "\r\n"
				+ "Use the random god gift in the order you want your stats to be.\r\n" + "\r\n" + godGift + "\r\n"
				+ "\r\n" + "Or write down those values that are agreed with your game master.\r\n" + "Examples:\r\n"
				+ " str 11 dex 12 con 13 int 14 wis 15 cha 16\r\n" + " 11, 12, 13, 14, 15, 16";

		return ReturnAct.builder().target(START_B).act(SingleAct.builder().name(name).text(text)
				.action(Action.builder().location(Location.STAT_FACTORY).mediator().build()).build()).build();
	}

	private List<Integer> statCompiler() {
		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(action.getAnswers()[0]);
		while (matcher.find()) {
			stats.add((Integer) Integer.parseInt(matcher.group()));
		}
		return stats;
	}

	private SingleAct apruveStats() {
		List<Integer> stats = statCompiler();
		if (stats.size() != 6) {
			String text = "Instructions not followed, please try again. Make sure there are 6 values.\r\n"
					+ "Examples:\r\n" + " 11, 12, 13, 14, 15, 16 \r\n" + " str 11 dex 12 con 13 int 14 wis 15 cha 16 ";
			return SingleAct.builder().name("DeadEnd").text(text).build();
		} else {
			String text = Stats.STRENGTH.toString() + " " + stats.get(0) + "\n" + Stats.CONSTITUTION.toString() + " "
					+ stats.get(1) + "\n" + Stats.DEXTERITY.toString() + " " + stats.get(2) + "\n"
					+ Stats.INTELLIGENSE.toString() + " " + stats.get(3) + "\n" + Stats.WISDOM.toString() + " "
					+ stats.get(4) + "\n" + Stats.CHARISMA.toString() + " " + stats.get(5) + "\n"
					+ "If you planned differently, write again";
			action.setMediator(false);
			action.setButtons(new String[][] { { "Yeah right" } });
			return SingleAct.builder().name("apruveStats").text(text).action(action).build();
		}
	}

	private Act finish(User user) {
		List<Integer> stats = statCompiler();
		LogicFactory.characteristic().initialize(user.getCharactersPool().getActual())
		.setup(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
		return new HpFactory(Action.builder().build()).executeFor(user);
	}

}
