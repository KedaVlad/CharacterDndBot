package app.dnd.service.logic.talants.feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.features.Feature;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.service.logic.ButtonLogic;
import app.dnd.service.logic.InformatorLogic;
import app.dnd.util.ButtonName;
import app.player.model.Stage;

public interface AbilityAction {

	BaseAction menu(Stage stage, Long id);
	BaseAction targetFeature(Stage stage);
}

@Component
class AbilityActor implements AbilityAction {

	@Autowired
	private ButtonLogic buttonBuilder;
	@Autowired
	private InformatorLogic informator;
	
	@Override
	public BaseAction menu(Stage stage, Long id) {
		
		Action action = (Action) stage;
		BaseAction[][] pool = null;
		String text = null;
		switch(action.getAnswers()[0]) {
		case ButtonName.FEATURE_B:
			text = informator.talants().ability().feature();
			pool = buttonBuilder.talants().ability().feature(id);
			break;
		case ButtonName.FEAT_B:
			text = informator.talants().ability().feat();
			pool = buttonBuilder.talants().ability().feat(id);
			break;
		case ButtonName.TRAIT_B:
			text = informator.talants().ability().trait();
			pool = buttonBuilder.talants().ability().trait(id);
			break;
		}
		return PoolActions.builder()
				.actionsPool(pool)
				.text(text)
				.build();
	}

	@Override
	public BaseAction targetFeature(Stage stage) {
		Action action = (Action) stage;
		Feature feature = (Feature) action.getObjectDnd();
		action.setButtons(buttonBuilder.talants().ability().singleFeature(feature));
		action.setText(informator.talants().ability().descriptionOfFeature(feature));
		return action;
	}
	
}