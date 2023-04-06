package app.dnd.service.logic.talants.prof;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.service.logic.ButtonLogic;
import app.dnd.service.logic.InformatorLogic;
import app.player.model.Stage;

public interface ProfAction {
	
	BaseAction menu(Stage stage, Long id);
	BaseAction create(Stage stage);
}

@Component
class ProfActor implements ProfAction {

	@Autowired
	private ButtonLogic buttonBuilder;
	@Autowired
	private InformatorLogic informator;
	
	@Override
	public BaseAction menu(Stage stage, Long id) {

		Action action = (Action) stage;
		action.setButtons(buttonBuilder.talants().proficiencies().possession(id));
		action.setText(informator.talants().proficiencies().possession(id));
		action.setReplyButtons(true);
		
		return action;
	}

	@Override
	public BaseAction create(Stage stage) {
		
		Action action = (Action) stage;
		action.setButtons(buttonBuilder.talants().proficiencies().create());
		action.setText(informator.talants().proficiencies().createInfo());
		action.setMediator(true);
		action.setReplyButtons(true);
		return action;
	}


}
