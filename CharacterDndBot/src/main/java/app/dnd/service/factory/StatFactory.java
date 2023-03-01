package app.dnd.service.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.ReturnAct;
import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.bot.model.user.User;
import app.dnd.dto.characteristics.Stat.Stats;
import app.dnd.service.Executor;
import app.dnd.service.Location;
import app.dnd.service.logic.characteristic.StatSetup;
import app.dnd.util.math.Formalizer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatFactory implements Executor<Action> {

	@Autowired
	private StartBuildStat startBuildStat;
	@Autowired
	private StatsCheckCondition statsCheckCondition;
	@Autowired
	private FinishBuildStat finishBuildStat;
	
	@Override
	public Act executeFor(Action action, User user) {

		switch (action.condition()) {
		case 0:
			return startBuildStat.executeFor(action, user);
		case 1:
			return statsCheckCondition.executeFor(action, user);
		case 2:
			return finishBuildStat.executeFor(action, user);
		}
		log.error("StatFactory: out of bounds condition");
		return null;
	}
}

@Component
class StartBuildStat implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {

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
}

@Component
class StatsCheckCondition implements Executor<Action> {

	@Autowired
	private StatTextCompiller statTextCompiller;
	
	@Override
	public Act executeFor(Action action, User user) {

			List<Integer> stats = statTextCompiller.compile(action.getAnswers()[0]);
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
	}


@Component 
class StatTextCompiller {
	
	public List<Integer> compile(String string) {
		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile("([0-9]{1,2})+?");
		Matcher matcher = pat.matcher(string);
		while (matcher.find()) {
			stats.add((Integer) Integer.parseInt(matcher.group()));
		}
		return stats;
	}
}


@Component
class FinishBuildStat implements Executor<Action> {

	@Autowired
	private StatTextCompiller statTextCompiller;
	@Autowired
	private StatSetup statSetup;
	@Autowired
	private HpFactory hpFactory;
	
	@Override
	public Act executeFor(Action action, User user) {

	List<Integer> stats = statTextCompiller.compile(action.getAnswers()[0]);
	statSetup.setup(user.getId(), stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
	return hpFactory.executeFor(Action.builder().build(), user);
}

}
