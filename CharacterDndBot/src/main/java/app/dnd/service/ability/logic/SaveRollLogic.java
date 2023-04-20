package app.dnd.service.ability.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.SaveRoll;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.service.ability.AbilityService;
import app.dnd.service.talants.logic.ProficienciesLogic;
import app.user.model.ActualHero;

public interface SaveRollLogic {

	void change(ActualHero actualHero, SaveRoll saveRoll);
	boolean maximum(SaveRoll article);

}

@Component
class SaveRollFacade implements SaveRollLogic {

	@Autowired
	private AbilityService abilityService;
	@Autowired
	private ProficienciesLogic proficienciesLogic;

	@Override
	public void change(ActualHero actualHero, SaveRoll saveRoll) {
		Ability characteristics = abilityService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		SaveRoll target = characteristics.getSaveRolls().get(saveRoll.getCore());
		target.setProficiency(saveRoll.getProficiency());
		proficienciesLogic.addPossession(actualHero, new Possession(saveRoll.getCore().toString(), saveRoll.getProficiency()));
		abilityService.save(characteristics);
	}
	
	@Override
	public boolean maximum(SaveRoll saveRoll) {
		if(saveRoll.getProficiency() == Proficiency.BASE) {
			return true;
		} else {
			return false;
		}
	}
}

