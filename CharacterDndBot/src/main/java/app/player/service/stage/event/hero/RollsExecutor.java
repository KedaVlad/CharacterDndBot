package app.player.service.stage.event.hero;

import app.dnd.model.actions.Action;
import app.dnd.util.math.Formalizer;
import app.player.event.StageEvent;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.ROLLS)
public class RollsExecutor implements Executor {
	
	@Override
	public Act execute(StageEvent event) {

		switch (((Action) event.getTusk()).condition()) {
			case 0 -> {
				return SingleAct.builder()
						.name(Button.ROLLS.NAME)
						.reply(true)
						.mediator(true)
						.stage(Action.builder()
								.location(Location.ROLLS)
								.text("""
										Choose a dice to throw, or write your own formula.
										To refer to a dice, use the D(or d) available dices you see in the console.
										For example: -d4 + 10 + 6d6 - 12 + d100""")
								.buttons(new String[][]{
										{"D4", "D6", "D8", "D10"},
										{"D12", "D20", "D100"},
										{Button.RETURN_TO_MENU.NAME}})
								.build())
						.build();
			}
			case 1 -> {
				return SingleAct.builder()
						.name("AfterRolls")
						.stage(Action.builder()
								.text(Formalizer.formalize(((Action) event.getTusk()).getAnswers()[0]))
								.build())
						.build();
			}
		}
		log.error("RollsMenu: out of bounds condition");
		return null;
	}

}
