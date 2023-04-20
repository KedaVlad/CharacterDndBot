package app.dnd.service.race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.util.ArrayToColumns;
import app.player.model.Stage;
import app.player.model.enums.Location;

@Component
public class RaceActor implements RaceAction {

	@Autowired
	private ArrayToColumns arrayToColumns; 
	@Autowired
	private RaceDndWrappService raceDndWrappService;
	
	@Override
	public Stage chooseRace() { 
		return Action.builder()
				.location(Location.RACE_FACTORY)
				.text("From what family you are?")
				.buttons(arrayToColumns.rebuild(raceDndWrappService.findDistinctRaceName().toArray(String[]::new),3, String.class))
				.build();
	}

	@Override
	public Stage chooseSubRace(Action action) {
		action.setButtons((String[][]) arrayToColumns.rebuild(raceDndWrappService.findDistinctSubRaceByRaceName(action.getAnswers()[0]).toArray(String[]::new),1, String.class));
		action.setText(action.getAnswers()[0] + "? More specifically?");
		return action;
	}

	@Override
	public Stage apruveRace(Action action) {
		action.setButtons(new String[][] { { "Yeah, right" } });
		String text = raceDndWrappService.findDistinctInformationByRaceNameAndSubRace(action.getAnswers()[0], action.getAnswers()[1]);
		action.setText(text + "\nIf not, select another option above.");
		return action;
	}

}
