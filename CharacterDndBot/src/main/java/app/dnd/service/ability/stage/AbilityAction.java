package app.dnd.service.ability.stage;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;

public interface AbilityAction {

	BaseAction menu();

	StatAction stat();

	SaveRollAction saveRoll();
	
	SkillAction skill();

	BaseAction buildAbilityPreRoll(Action action);

}


