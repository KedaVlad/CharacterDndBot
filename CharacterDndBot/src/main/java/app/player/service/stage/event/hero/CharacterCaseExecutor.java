package app.player.service.stage.event.hero;

import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.service.DndFacade;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.ArrayActs;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;

@EventExecutor(Location.CHARACTER_CASE)
public class CharacterCaseExecutor implements Executor {

	@Autowired
	private DndFacade dndFacade;

	@Override
	public Act execute(StageEvent event) {
		
		if(dndFacade.hero().isEmpty(event.getUser().getId())) {
			return SingleAct.builder()
					.name(Button.CHARACTER_CASE.NAME)
					.stage(dndFacade.action().create(event.getUser().getId()))
					.build();

		} else {
			return ReturnAct.builder()
					.target(Button.START.NAME)
					.act(ArrayActs.builder()
							.name(Button.CHARACTER_CASE.NAME)
							.pool(dndFacade.action().create(event.getUser().getId()),
									dndFacade.action().heroList(event.getUser().getId()))
							.build())
					.build();

		}
	}
}
