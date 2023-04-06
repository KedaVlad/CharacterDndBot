package app.dnd.service.logic.talants.prof;

import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.model.ability.proficiency.Possession;

public interface ProficienciesLogic {

	public void addPossession(Long id, Possession possession);
}

class ProficienciesFacade implements ProficienciesLogic {
	
	@Autowired
	private ProficienciesAddPossession proficienciesAddPossession;

	@Override
	public void addPossession(Long id, Possession possession) {
		proficienciesAddPossession.add(id, possession);
	}
}