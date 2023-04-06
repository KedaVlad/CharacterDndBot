package app.dnd.service.logic.characteristic.saveroll;

import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.model.ability.proficiency.Possession;
import app.dnd.model.characteristics.Characteristics;
import app.dnd.model.characteristics.SaveRoll;
import app.dnd.service.logic.characteristic.CharacteristicsService;
import app.dnd.service.logic.talants.prof.ProficienciesLogic;

public class SaveRollUp {

	@Autowired
	private ProficienciesLogic proficienciesLogic;
	@Autowired
	private CharacteristicsService characteristicsService;

	public void up(SaveRoll saveRoll, Long id) {

		Characteristics characteristics = characteristicsService.getById(id);
		SaveRoll target = characteristics.getSaveRolls().get(saveRoll.getCore());
		target.setProficiency(saveRoll.getProficiency());
		proficienciesLogic.addPossession(id, new Possession(saveRoll.getCore().toString(), saveRoll.getProficiency()));
		characteristicsService.save(characteristics);
	}

}
