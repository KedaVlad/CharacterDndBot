package app.dnd.service.logic.characteristic.saveroll;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.SaveRoll;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.SaveRolls;
import app.dnd.service.logic.characteristic.CharacteristicsService;
import app.dnd.service.logic.talants.prof.ProficienciesService;
import app.dnd.util.ArrayToColumns;
import app.player.model.enums.Location;

public interface SaveRollButton {
	public BaseAction[][] menu(Long id);
	public String[][] targetChange(SaveRoll saveRoll);
}

@Component
class SaveRollsButtonBuilder implements SaveRollButton {

	@Autowired
	private MenuSaveRollButtonBuilder menuSaveRollButtonBuilder;
	@Autowired
	private SaveRollSingleButtonBuilder saveRollSingleButtonBuilder;
	
	
	@Override
	public BaseAction[][] menu(Long id) {
		return menuSaveRollButtonBuilder.build(id);
	}

	@Override
	public String[][] targetChange(SaveRoll saveRoll) {
		return saveRollSingleButtonBuilder.build(saveRoll);
	}
}

class MenuSaveRollButtonBuilder {

	@Autowired
	private CharacteristicsService characteristicsService;
	@Autowired
	private ArrayToColumns arrayToColumns;
	@Autowired
	private ProficienciesService proficienciesService;

	public BaseAction[][] build(Long id) {

		Characteristics characteristics = characteristicsService.getById(id); 
		Proficiencies proficiencies = proficienciesService.getById(id);
		Map<SaveRolls, SaveRoll> saveRolls = characteristics.getSaveRolls();
		BaseAction[] pool = new BaseAction[saveRolls.keySet().size()];
		int i = 0;
		for(SaveRolls saveRoll: saveRolls.keySet()) {
			pool[i] = Action.builder()
					.name((characteristics.modificator(saveRolls.get(saveRoll).getCore().getStat()) + proficiencies.getProfBonus(saveRolls.get(saveRoll).getProficiency())) + " " + saveRoll.toString())
					.objectDnd(saveRolls.get(saveRoll))
					.location(Location.CHARACTERISTIC)
					.build();
			i++;
		}
		return arrayToColumns.rebuild(pool, 2, BaseAction.class);
	}
}

@Component
class SaveRollSingleButtonBuilder {

	public String[][] build(SaveRoll saveRoll) {

		if(saveRoll.getProficiency() == Proficiency.BASE) {
				return new String[][] {{"Up to PROFICIENCY"}};
		} else {
			return null;
		}
	}
}
