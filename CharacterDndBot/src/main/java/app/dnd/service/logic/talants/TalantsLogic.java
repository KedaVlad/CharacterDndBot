package app.dnd.service.logic.talants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.service.logic.talants.prof.ProficienciesLogic;

public interface TalantsLogic {

	public ProficienciesLogic proficiencies();
}

@Component
class TalantsFacade implements TalantsLogic {
	
	@Autowired
	private ProficienciesLogic proficienciesLogic;

	@Override
	public ProficienciesLogic proficiencies() {
		return proficienciesLogic;
	}
}