package app.player.service.stage.event.hero;

import java.util.UUID;
import app.dnd.model.actions.Action;
import app.player.event.StageEvent;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.CloudAct;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventExecutor(Location.DEBUFF)
public class DebuffExecutor implements Executor {
	
	@Override
	public Act execute(StageEvent event) {
		
		Action action = (Action) event.getTusk();
		if(action.condition() == 0) {
			return ReturnAct.builder()
					.target(Button.MENU.NAME)
					.act(SingleAct.builder()
							.name("Debuff")
							.mediator(true)
							.reply(true)
							.stage(Action.builder()
									.text("(Write) What is effect on you? After it will end ELIMINATE this...")
									.location(Location.DEBUFF)
									.buttons(new String[][] {{Button.RETURN_TO_MENU.NAME}})
									.build())
							.build())
					.build();
		} else if (action.condition() == 1) {

			String name = UUID.randomUUID().toString().substring(0, 5);
			return CloudAct.builder()
					.name(name)
					.stage(Action.builder()
							.text(action.getAnswers()[0])
							.build())
					.build();
		} else {
			log.error("DebuffExecutor : no valid condition");
			return ReturnAct.builder().target(Button.MENU.NAME).build();
		}
		
	}

}