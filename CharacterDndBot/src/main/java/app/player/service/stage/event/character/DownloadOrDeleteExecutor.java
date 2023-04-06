package app.player.service.stage.event.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.service.CharactersPoolControler;
import app.dnd.model.actions.Action;
import app.player.model.act.Act;
import app.player.model.act.ReturnAct;
import app.player.model.act.SingleAct;
import app.player.service.stage.Executor;
import app.player.service.stage.event.factory.ClassFactory;
import app.player.service.stage.event.factory.HpFactory;
import app.player.service.stage.event.factory.RaceFactory;
import app.player.service.stage.event.factory.StatFactory;
import app.user.model.User;

@Service
public class DownloadOrDeleteExecutor  implements Executor<Action> {

	@Autowired
	private DownloadOrDeleteMenu downloadOrDeleteMenu;
	@Autowired
	private DownloadHero downloadHero;
	@Autowired
	private StartDeleteHero startDeleteHero;
	@Autowired
	private EndDeleteHero endDeleteHero;

	@Override
	public Act executeFor(Action action, User user) {

		int condition = action.condition();
		if (condition == 1) {
			return downloadOrDeleteMenu.executeFor(action, user);
		} else if (condition == 2) {
			if (action.getAnswers()[1].equals(DOWNLOAD_B)) {
				return downloadHero.executeFor(action, user);
			} else if (action.getAnswers()[1].equals(DELETE_B)) {
				return startDeleteHero.executeFor(action, user);
			} else {
				return ReturnAct.builder().target(CHARACTER_CASE_B).build();
			}
		} else {
			return endDeleteHero.executeFor(action, user);
		}
	}
}

@Component
class DownloadOrDeleteMenu implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		action.setButtons(new String[][] {{DOWNLOAD_B, DELETE_B},{"RETURN TO CREATING"}});
		action.setReplyButtons(true);
		return SingleAct.builder()
				.name("DownloadOrDeleteMenu")
				.text(action.getAnswers()[0])
				.action(action)
				.build();
	}
}

@Component
class DownloadHero implements Executor<Action> {

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
	@Autowired
	private CharactersPoolControler charactersPoolControler;
	
	@Override
	public Act executeFor(Action action, User user) {
		
		charactersPoolControler.download(user, action.getAnswers()[0]);
		if(user.getActualHero().hasReadyHero()) {
			return menu.executeFor(Action.builder().build(), user);
		} else if(user.getActualHero().getCharacter().getRace() == null) {
			return raceFactory.executeFor(Action.builder().build(), user);
		} else if(user.getActualHero().getCharacter().getDndClass().isEmpty()) {
			return classFactory.executeFor(Action.builder().build(), user);
		} else if(user.getActualHero().getCharacter().getCharacteristics().getStats()[0].getValue() == 0) {
			return statFactory.executeFor(Action.builder().build(), user);
		} else {
			return hpFactory.executeFor(Action.builder().build(), user);
		}
	}
}

@Component
class StartDeleteHero implements Executor<Action> {

	@Override
	public Act executeFor(Action action, User user) {
		action.setButtons(new String[][] {{"Yes","No"}});
		action.setReplyButtons(true);
		return SingleAct.builder()
				.name("StartDeleteHero")
				.text("Are you sure? After that, you will not be able to restore the hero.")
				.action(action)
				.build();
	}
}

@Component
class EndDeleteHero implements Executor<Action> {

	@Autowired
	private CharactersPoolControler charactersPoolControler;
	
	@Override
	public Act executeFor(Action action, User user) {
		if(action.getAnswers()[2].equals("Yes")) {
			charactersPoolControler.delete(user.getId(), action.getAnswers()[0]);
			return ReturnAct.builder().target(START_B).build();
		} else {
			return ReturnAct.builder().target("DownloadOrDeleteMenu").build();
		}
	}
}
