package app.dnd.service.talants.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TalantsFacade implements TalantsLogic {
	
	@Autowired
	private ProficienciesLogic proficienciesLogic;
	@Autowired
	private MagicSoulLogic magicSoulLogic;
	@Autowired
	private FeatureLogic featureLogic;
	
	@Override
	public ProficienciesLogic proficiencies() {
		return proficienciesLogic;
	}

	@Override
	public MagicSoulLogic magic() {
		return magicSoulLogic;
	}

	@Override
	public FeatureLogic feature() {
		return featureLogic;
	}

}
