package app.player.service.stage.event.hero;

import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.model.actions.Action;
import app.dnd.service.DndFacade;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.SingleAct;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;

@EventExecutor(Location.TEXT_COMMAND)
public class TextComandManager implements Executor {

	@Autowired
	private DndFacade dndFacade;
	@Autowired
	private Menu menu;
	
	
	@Override
	public Act execute(StageEvent event) {

		Action action = (Action) event.getTusk();
		String text = action.getAnswers()[0];
		if(event.getUser().getActualHero().isReadyToGame()) {

			if (text.matches("^(\\+|\\+\\+|-)\\d{1,3}")) {
				int exp = (Integer) Integer.parseInt(action.getAnswers()[0].replaceAll("^(?i)exp(\\d+)", "$1"));
				if (dndFacade.hero().lvl().addExperience(event.getUser().getActualHero(), exp)) {

				}
				return menu.execute(event);
				
			} else if (text.matches("^(?i)exp\\d{1,6}")) {
				
				String num = action.getAnswers()[0].replaceAll("^(\\+\\+|\\+|-)(\\d+)", "$2");
				int value = (Integer) Integer.parseInt(num);
				if (action.getAnswers()[0].contains("++")) {
					dndFacade.hero().hp().bonusHp(event.getUser().getActualHero(), value);
				} else if (action.getAnswers()[0].contains("+")) {
					dndFacade.hero().hp().heal(event.getUser().getActualHero(), value);
				} else if (action.getAnswers()[0].contains("-")) {
					dndFacade.hero().hp().damage(event.getUser().getActualHero(), value);
				} else {
					return SingleAct.builder()
							.name("MissHpFormula")
							.stage(Action.builder()
									.text("You make something vrong... I dont understand what do whith " + value + " heal(+) or damage(-)? Try again.")
									.build())
							.build();
				}
				return menu.execute(event);
				
			} else {
				dndFacade.hero().memoirs().addMemoirs(event.getUser().getActualHero(), text);
				return SingleAct.builder()
						.name("addMemoirs")
						.stage(Action.builder()
								.text("I will put it in your memoirs")
								.build())
						.build();
			}
			
		} else {
			return SingleAct.builder().name("DeadEnd")
					.stage(Action.builder()
							.text("Until you don`t have active Hero i can`t write memoirs about them... So, this text recognize OBLIVION!!!")
							.build())
					.build();
		}
	}

}
