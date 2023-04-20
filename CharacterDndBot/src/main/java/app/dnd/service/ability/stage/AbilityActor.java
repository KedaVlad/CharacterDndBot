package app.dnd.service.ability.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.SaveRoll;
import app.dnd.model.ability.Skill;
import app.dnd.model.ability.Stat;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.actions.RollAction;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.Stats;
import app.player.model.enums.Button;
import app.player.model.enums.Location;

@Component
public class AbilityActor implements AbilityAction {

	@Autowired
	private StatAction statAction;
	@Autowired
	private SaveRollAction saveRollAction;
	@Autowired
	private  SkillAction skillAction;
	
	@Override
	public BaseAction menu() {
		return Action.builder()
				.location(Location.ABILITY)
				.buttons(new String[][]{{Button.STAT.NAME, Button.SAVE_ROLL.NAME, Button.SKILL.NAME},{Button.RETURN_TO_MENU.NAME}})
				.text("Here you can operate your abilities, rollig and changing value")
				.build(); 
	}

	@Override
	public BaseAction buildAbilityPreRoll(Action action) {
		Stats stat = null;
		Proficiency proficiency = null;
		if (action.getObjectDnd() instanceof Stat) {
			stat = ((Stat)action.getObjectDnd()).getCore();
		} else if (action.getObjectDnd() instanceof Skill) {
			stat = ((Skill)action.getObjectDnd()).getCore().getStat();
			proficiency = ((Skill)action.getObjectDnd()).getProficiency();
		} else if (action.getObjectDnd() instanceof SaveRoll) {
			stat = ((SaveRoll)action.getObjectDnd()).getCore().getStat();
			proficiency = ((SaveRoll)action.getObjectDnd()).getProficiency();
		} 
		
		return PreRoll.builder()
				.text("... or roll.")
				.location(Location.ABILITY)
				.roll(RollAction.buider()
						.statDepend(stat)
						.proficiency(proficiency)
						.location(Location.ABILITY)
						.build())
				.build();
	}

	@Override
	public StatAction stat() {
		return statAction;
	}

	@Override
	public SaveRollAction saveRoll() {
		return saveRollAction;
	}

	@Override
	public SkillAction skill() {
		return skillAction;
	}


}
