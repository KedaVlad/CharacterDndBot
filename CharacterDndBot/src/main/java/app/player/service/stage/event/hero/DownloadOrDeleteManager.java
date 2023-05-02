package app.player.service.stage.event.hero;

import app.player.model.event.StageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.service.DndFacade;
import app.player.model.EventExecutor;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.player.service.stage.Executor;
import app.player.service.stage.event.factory.ClassFactory;
import app.player.service.stage.event.factory.HpFactory;
import app.player.service.stage.event.factory.RaceFactory;
import app.player.service.stage.event.factory.StatFactory;
import app.bot.model.user.ActualHero;

@EventExecutor(Location.DOWNLOAD_OR_DELETE)
public class DownloadOrDeleteManager implements Executor {

	@Autowired
	private DownloadOrDeleteExecutor downloadOrDeleteExecutor;

	@Override
	public Act execute(StageEvent event) {

		Action action = (Action) event.getTusk();

		if (action.condition() == 1) {
			return downloadOrDeleteExecutor.menu(action);
		} else if (action.condition() == 2) {
			if (action.getAnswers()[1].equals(Button.DOWNLOAD.NAME)) {
				return downloadOrDeleteExecutor.download(event, action.getAnswers()[0]);
			} else if (action.getAnswers()[1].equals(Button.DELETE.NAME)) {
				return downloadOrDeleteExecutor.startDelete(action);
			} else {
				return ReturnAct.builder().target(Button.CHARACTER_CASE.NAME).build();
			}
		} else {
			return downloadOrDeleteExecutor.endDelete(event.getUser().getActualHero(), action);
		}
	}

}

@Component
class DownloadOrDeleteExecutor {

	@Autowired
	private DndFacade dndFacade;
	@Autowired
	private Menu menu;
	@Autowired
	private ClassFactory classFactory;
	@Autowired
	private RaceFactory raceFactory;
	@Autowired
	private StatFactory statFactory;
	@Autowired
	private HpFactory hpFactory;
	
	public Act menu(Action action) {
		
			action.setButtons(new String[][] {{Button.DOWNLOAD.NAME, Button.DELETE.NAME},{Button.RETURN_TO_CREATING.NAME}});
			action.setText(action.getAnswers()[0]);
			return SingleAct.builder()
					.name("DownloadOrDeleteMenu")
					.reply(true)
					.stage(action)
					.build();
		}
	
	public Act download(StageEvent event, String heroName) {
	
		
		dndFacade.hero().download(event.getUser().getActualHero(), heroName);
		if(event.getUser().getActualHero().isReadyToGame()) {
			return menu.execute(event);
			
		} else if(!dndFacade.hero().race().isReadyToGame(event.getUser().getActualHero())) {
			return raceFactory.execute(new StageEvent (this, event.getUser(), Action.builder().build()));
			
		} else if(!dndFacade.hero().classes().isReadyToGame(event.getUser().getActualHero())) {
			return classFactory.execute(new StageEvent (this, event.getUser(), Action.builder().build()));
			
		} else if(!dndFacade.hero().ability().stat().isReadyToGame(event.getUser().getActualHero())) {
			return statFactory.execute(new StageEvent (this, event.getUser(), Action.builder().build()));
			
		} else {
			return hpFactory.execute(new StageEvent (this, event.getUser(), Action.builder().build()));
		}
	}
	
	public Act startDelete(Action action) {
		action.setButtons(new String[][] {{"Yes","No"}});
		action.setText("Are you sure? After that, you will not be able to restore the hero.");
		return SingleAct.builder()
				.name("StartDeleteHero")
				.reply(true)
				.stage(action)
				.build();
	}
	
	public Act endDelete(ActualHero hero, Action action) {
		if(action.getAnswers()[2].equals("Yes")) {
			dndFacade.hero().delete(hero , action.getAnswers()[0]);
			return ReturnAct.builder().target(Button.START.NAME).build();
		} else {
			return ReturnAct.builder().target(Button.CHARACTER_CASE.NAME).build();
		}
	}
}

