package app.dnd.service.ability.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.Stat;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.SingleAction;
import app.dnd.model.enums.Stats;
import app.dnd.service.ability.AbilityService;
import app.dnd.util.ArrayToColumns;
import app.dnd.util.math.Formalizer;
import app.player.model.Stage;
import app.player.model.enums.Location;
import app.user.model.ActualHero;

public interface StatAction {
	
	BaseAction menu(ActualHero hero);

	BaseAction singleStat(Stage stage);

	Stage factoryStatBuild();

	Stage checkStatCondition(Action action);
}

@Component
class StatActor implements StatAction {
	
	@Autowired
	private StatButtonsBuilder statButtonsBuilder;

	@Override
	public BaseAction menu(ActualHero hero) {
		return PoolActions.builder()
				.text("Choose stat which you want to roll or change")
				.actionsPool(statButtonsBuilder.menu(hero))
				.build();
	}

	@Override
	public BaseAction singleStat(Stage stage) {
		
		Action action = (Action) stage;
		Stat stat = (Stat) action.getObjectDnd();
		action.setButtons(statButtonsBuilder.targetChangeButtons(stat));
		action.setText(stat.getCore().toString() + " " + stat.getValue() + ". If u have reason to change value...");
		return action;
	}

	@Override
	public Stage factoryStatBuild() {
		
		String godGift = Formalizer.randomStat() + ", " + Formalizer.randomStat() + ", " + Formalizer.randomStat()
		+ ", " + Formalizer.randomStat() + ", " + Formalizer.randomStat() + ", " + Formalizer.randomStat();

		String text = "Now let's see what you have in terms of characteristics.\r\n" + "\r\n"
				+ "Write the value of the characteristics in order: Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma.\r\n"
				+ "1.Each stat cannot be higher than 20.\r\n"
				+ "2. Write down stats without taking into account buffs from race / class.\r\n" + "\r\n"
				+ "Use the random god gift in the order you want your stats to be.\r\n" + "\r\n" + godGift + "\r\n"
				+ "\r\n" + "Or write down those values that are agreed with your game master.\r\n" + "Examples:\r\n"
				+ " str 11 dex 12 con 13 int 14 wis 15 cha 16\r\n" + " 11, 12, 13, 14, 15, 16";
		
		return 	Action.builder()
				.location(Location.STAT_FACTORY)
				.text(text)
				.build();
	}

	@Override
	public Stage checkStatCondition(Action action) {
		
		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile("([0-9]{1,2})+?");
		Matcher matcher = pat.matcher(action.getAnswers()[0]);
		while (matcher.find()) {
			stats.add((Integer) Integer.parseInt(matcher.group()));
		}
		if (stats.size() != 6) {
			String text = "Instructions not followed, please try again. Make sure there are 6 values.\r\n"
					+ "Examples:\r\n" + " 11, 12, 13, 14, 15, 16 \r\n" + " str 11 dex 12 con 13 int 14 wis 15 cha 16 ";
			return Action.builder().text(text).build();
		} else {
			action.setText(Stats.STRENGTH.toString() + " " + stats.get(0) + "\n" + Stats.CONSTITUTION.toString() + " "
					+ stats.get(1) + "\n" + Stats.DEXTERITY.toString() + " " + stats.get(2) + "\n"
					+ Stats.INTELLIGENSE.toString() + " " + stats.get(3) + "\n" + Stats.WISDOM.toString() + " "
					+ stats.get(4) + "\n" + Stats.CHARISMA.toString() + " " + stats.get(5) + "\n"
					+ "If you planned differently, write again");
			action.setButtons(new String[][] { { "Yeah right" } });
			return action;
		}
	}
	
}

@Component
class StatButtonsBuilder {

	@Autowired
	private ArrayToColumns arrayToColumns;
	@Autowired
	private AbilityService abilityService;

	public SingleAction[][] menu(ActualHero hero) {

		Ability characteristics = abilityService.findByIdAndOwnerName(hero.getId(), hero.getName());
		Map<Stats, Stat> stats = characteristics.getStats();
		SingleAction[] arr = new SingleAction[stats.size()];
		
		int i = 0;
		for(Stats stat: stats.keySet()) {
			arr[i] = Action.builder()
					.name(stats.get(stat).getValue() + "["+ characteristics.modificator(stat) + "] " + stats.get(stat).getCore())
					.objectDnd(stats.get(stat))
					.location(Location.ABILITY)
					.build();
			i++;
		}
		return arrayToColumns.rebuild(arr, 2, SingleAction.class);
	}

	public String[][] targetChangeButtons(Stat stat) {
		String[][] base = new String[][] {{"-3","-2","-1","+1","+2","+3"}};
		int max = stat.getMaxValue() - stat.getValue();
		int min = stat.getValue() - 3;
		if(max > 3) max = 3;
		if(min > 3) min = 3;
		String[][] answer = new String[1][min+max];
		if(answer[0].length == 6) {
			return base;
		} else if(min < max) {
			int j = 0;
			for(int i = 6 - answer[0].length; i < 6; i++) {
				answer[0][j] = base[0][i];
				j++;
			}
		} else {
			for(int i = 0; i < answer[0].length; i ++) {
				answer[0][i] = base[0][i];
			}
		}
		return answer;
	}
	
}
