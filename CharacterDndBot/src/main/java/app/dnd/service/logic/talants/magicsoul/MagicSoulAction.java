package app.dnd.service.logic.talants.magicsoul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.spells.Spell;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.service.logic.ButtonLogic;
import app.dnd.service.logic.InformatorLogic;
import app.player.model.Stage;

public interface MagicSoulAction {
	
	BaseAction menu(Stage stage, Long id);
	BaseAction targetSpell(Stage stage);
}

@Component
class MagicSoulActor implements MagicSoulAction {

	@Autowired
	private ButtonLogic buttonBuilder;
	@Autowired
	private InformatorLogic informator;
	
	@Override
	public BaseAction menu(Stage stage, Long id) {
		return PoolActions.builder()
				.actionsPool(buttonBuilder.talants().magicSoul().spell(id))
				.text(informator.talants().magicSoul().spell())
				.build();
	}

	@Override
	public BaseAction targetSpell(Stage stage) {
		
		Action action = (Action) stage;
		action.setButtons(buttonBuilder.talants().magicSoul().singleSpell((Spell) action.getObjectDnd()));
		action.setText(informator.talants().magicSoul().descriptionOfSpell((Spell) action.getObjectDnd()));
		return action;
	}
	
}