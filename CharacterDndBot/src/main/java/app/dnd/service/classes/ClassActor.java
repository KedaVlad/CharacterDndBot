package app.dnd.service.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.util.ArrayToColumns;
import app.player.model.Stage;
import app.player.model.enums.Location;
@Component
public class ClassActor implements ClassAction {

	@Autowired
	private ArrayToColumns arrayToColumns;
	@Autowired
	private ClassDndWrappService classDndWrappService;

	@Override
	public Stage chooseClass() {
		return Action.builder()
				.text("What is your class?")
				.location(Location.CLASS_FACTORY)
				.buttons((String[][]) arrayToColumns.rebuild(classDndWrappService.findDistinctClassName().toArray(String[]::new), 1, String.class))
				.build();
	}

	@Override
	public Stage chooseSubClass(Action action) {
		action.setButtons((String[][]) arrayToColumns.rebuild(classDndWrappService.findDistinctArchetypeByClassName(action.getAnswers()[0]).toArray(String[]::new),1, String.class));
		action.setText(action.getAnswers()[0] + ", realy? Which one?");
		return action;
	}

	@Override
	public Stage apruve(Action action) {
		int lvl = 0;
		Pattern pat = Pattern.compile("([0-9]{1,2})+?");
		Matcher matcher = pat.matcher(action.getAnswers()[2]);
		while (matcher.find()) {
			lvl = ((Integer) Integer.parseInt(matcher.group()));
		}

		if (0 < lvl && lvl <= 20) {
			action.setButtons(new String[][] { { "Yeah, right" } });
			action.setText(classDndWrappService.findDistinctInformationByClassNameAndArchetype(action.getAnswers()[0], action.getAnswers()[1])
					+ "\nIf not, select another option above.");
		} else {
			action.getAnswers()[2] = ""+1;
			action.setButtons(new String[][] { { "Okay" } });
			action.setText(lvl + "??? I see you're new here. Let's start with lvl 1.\nAre you satisfied with this option?\n"
					+ classDndWrappService.findDistinctInformationByClassNameAndArchetype(action.getAnswers()[0], action.getAnswers()[1])
					+ "\nIf not, select another option above.");
		}
		return action;
	}
}
