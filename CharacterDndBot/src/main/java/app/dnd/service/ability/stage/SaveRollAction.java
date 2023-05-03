package app.dnd.service.ability.stage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.SaveRoll;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.SingleAction;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.SaveRolls;
import app.dnd.model.telents.proficiency.Proficiencies;
import app.dnd.service.ability.AbilityService;
import app.dnd.service.talants.ProficienciesService;
import app.dnd.util.ArrayToColumns;
import app.player.model.Stage;
import app.player.model.enums.Location;
import app.bot.model.user.ActualHero;

public interface SaveRollAction {
	
	Stage menu(ActualHero actualHero);
	
	BaseAction singleSaveRoll(SaveRoll stage);

	
}

@Component
class SaveRollActor implements SaveRollAction {
	
	@Autowired
	private SaveRollsButtonBuilder saveRollsButtonBuilder;
	
	@Override
	public BaseAction menu(ActualHero actualHero) {
		return PoolActions.builder()
				.text("Choose Save Roll which you want to roll or change")
				.actionsPool(saveRollsButtonBuilder.menu(actualHero.getId(), actualHero.getName()))
				.build();
	}
	@Override
	public BaseAction singleSaveRoll(SaveRoll saveRoll) {
		
		return Action.builder()
				.location(Location.ABILITY)
				.text("If you have a reason to improve ...")
				.buttons(saveRollsButtonBuilder.targetChange(saveRoll))
				.build();
	}

}

@Component
class SaveRollsButtonBuilder {

	@Autowired
	private AbilityService characteristicsService;
	@Autowired
	private ArrayToColumns arrayToColumns;
	@Autowired
	private ProficienciesService proficienciesService;
	
	public SingleAction[][] menu(Long id, String name) {
		Ability characteristics = characteristicsService.findByIdAndOwnerName(id, name); 
		Proficiencies proficiencies = proficienciesService.findByIdAndOwnerName(id, name);
		Map<SaveRolls, SaveRoll> saveRolls = characteristics.getSaveRolls();
		SingleAction[] pool = new SingleAction[saveRolls.keySet().size()];
		int i = 0;
		for(SaveRolls saveRoll: saveRolls.keySet()) {
			pool[i] = Action.builder()
					.name((characteristics.modification(saveRolls.get(saveRoll).getCore().getStat()) + proficiencies.getProfBonus(saveRolls.get(saveRoll).getProficiency())) + " " + saveRoll.getName())
					.objectDnd(saveRolls.get(saveRoll))
					.location(Location.ABILITY)
					.build();
			i++;
		}
		return arrayToColumns.rebuild(pool, 2, SingleAction.class);
	}

	public String[][] targetChange(SaveRoll saveRoll) {
		if(saveRoll.getProficiency() == Proficiency.BASE) {
			return new String[][] {{"Up to PROFICIENCY"}};
		} else {
			return null;
		}
	}
}
