package app.dnd.service.hp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.player.model.Stage;
import app.player.model.enums.Location;
import app.bot.model.user.ActualHero;

@Component
public class HpActor implements HpAction {

	@Autowired
	private HpLogic hpLogic;
	
	@Override
	public Stage startBuildHp(ActualHero hero) {
		int stableHp = hpLogic.buildValueStableHp(hero);
		String[][] nextStep = { { "Stable " + stableHp, "Random ***" } };
		String text = "How much HP does your character have?\r\n" + "\r\n" + "You can choose stable or random HP count \r\n"
				+ "\r\n"
				+ "If you agreed with the game master on a different amount of HP, send its value. (Write the amount of HP)";

		return Action.builder()
				.text(text)
				.buttons(nextStep)
				.location(Location.HP_FACTORY)
				.build();
		
	}

	@Override
	public Stage apruveHp(ActualHero actualHero, Action action) {
		String answer = action.getAnswers()[0];
		String text = "Congratulations, you are ready for adventure.";

		if (answer.contains("Stable")) {
			action = action.continueStage(hpLogic.buildValueStableHp(actualHero) + "");
		} else if(answer.contains("Random")) {
			action = action.continueStage(hpLogic.buildValueRandomHp(actualHero) + "");
		} else {
			Pattern pat = Pattern.compile("([0-9]{1,4})+?");
			Matcher matcher = pat.matcher(answer);
			int hp = 0;
			while (matcher.find()) {
				hp = ((Integer) Integer.parseInt(matcher.group()));
			}
			if (hp <= 0) {
				action = action.continueStage(hpLogic.buildValueStableHp(actualHero) + "");
				text = "Nice try... I see U very smart, but you will get stable " + hp
						+ " HP. You are ready for adventure.";
			} else {
				action = action.continueStage(hp + "");
			}
		}
		
		action.setText(text);
		action.setButtons(new String[][] { { "Let's go" } });
		return action;
	}

}
