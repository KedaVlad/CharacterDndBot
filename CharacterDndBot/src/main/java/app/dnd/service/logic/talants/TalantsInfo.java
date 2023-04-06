package app.dnd.service.logic.talants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.service.logic.talants.feature.AbilityInfo;
import app.dnd.service.logic.talants.magicsoul.MagicSoulInfo;
import app.dnd.service.logic.talants.prof.ProficienciesInfo;
import app.dnd.util.ButtonName;

public interface TalantsInfo {

	public String menu();
	public AbilityInfo ability();
	public MagicSoulInfo magicSoul();
	public ProficienciesInfo proficiencies();
}

@Component
class TalantsInformator implements TalantsInfo {
	
	@Autowired
	private AbilityInfo abilityInfo;
	@Autowired
	private MagicSoulInfo magicSoulInfo;
	@Autowired
	private ProficienciesInfo proficienciesInfo;
	
	@Override
	public String menu() {
		return "Here, you can access and manage your character's ta talents, including " 
				+ ButtonName.TRAIT_B + ", " + ButtonName.FEATURE_B + ", " + ButtonName.FEAT_B + ", " + ButtonName.POSSESSION_B 
				+ " and " + ButtonName.SPELL_B;
	}

	@Override
	public AbilityInfo ability() {
		return abilityInfo;
	}

	@Override
	public MagicSoulInfo magicSoul() {
		return magicSoulInfo;
	}

	@Override
	public ProficienciesInfo proficiencies() {
		return proficienciesInfo;
	}

}