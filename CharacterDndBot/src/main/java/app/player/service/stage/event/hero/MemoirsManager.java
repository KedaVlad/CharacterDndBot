package app.player.service.stage.event.hero;

import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.service.DndFacade;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;

@EventExecutor(Location.MEMOIRS)
public class MemoirsManager implements Executor {

	@Autowired
	private DndFacade dndFacade;
	
	@Override
	public Act execute(StageEvent event) {
	
		return ReturnAct.builder()
				.target(Button.MENU.NAME)
				.act(SingleAct.builder()
						.name(Button.MEMOIRS.NAME)
						.reply(true)
						.stage(dndFacade.action().memoirs().menu(event.getUser().getActualHero()))
						.build())
				.build();
	}

}
