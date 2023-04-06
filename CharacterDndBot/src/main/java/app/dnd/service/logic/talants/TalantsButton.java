package app.dnd.service.logic.talants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.spells.MagicSoul;
import app.dnd.service.logic.talants.feature.AbilityButtons;
import app.dnd.service.logic.talants.feature.AbilityService;
import app.dnd.service.logic.talants.magicsoul.MagicSoulButton;
import app.dnd.service.logic.talants.magicsoul.MagicSoulService;
import app.dnd.service.logic.talants.prof.ProficienciesButton;
import app.dnd.util.ButtonName;

public interface TalantsButton {
	
	public String[][] menu(Long id);
	public MagicSoulButton magicSoul();
	public AbilityButtons ability();
	public ProficienciesButton proficiencies();
}

@Component
class TalantsButtonBuilder implements TalantsButton{
	
	@Autowired
	public AbilityService abilityService;
	@Autowired
	public MagicSoulService magicSoulService;
	@Autowired
	public MagicSoulButton magicSoulButton;
	@Autowired
	public AbilityButtons abilityButtons;
	@Autowired
	public ProficienciesButton proficienciesButton;

	@Override
	public AbilityButtons ability() {
		return abilityButtons;
	}

	@Override
	public MagicSoulButton magicSoul() {
		return magicSoulButton;
	}

	@Override
	public ProficienciesButton proficiencies() {
		return proficienciesButton;
	}

	
	@Override
	public String[][] menu(Long id) {
		
		Ability ability = abilityService.getById(id);
		MagicSoul magicSoul = magicSoulService.getById(id);
		
		if (magicSoul.getCells() == null && ability.getFeats().size() == 0) {
			return new String[][] {{ButtonName.TRAIT_B, ButtonName.FEATURE_B, ButtonName.POSSESSION_B }, { ButtonName.RETURN_TO_MENU }};
		} else if(magicSoul.getCells() == null) {
			return new String[][] {{ ButtonName.FEATURE_B, ButtonName.POSSESSION_B }, {ButtonName.TRAIT_B, ButtonName.FEAT_B}, { ButtonName.RETURN_TO_MENU }};
		} else if(ability.getFeats().size() == 0) {
			return new String[][] {{ ButtonName.TRAIT_B, ButtonName.POSSESSION_B }, {ButtonName.FEATURE_B, ButtonName.SPELL_B}, { ButtonName.RETURN_TO_MENU }};
		} else {
			return new String[][] {{ ButtonName.TRAIT_B, ButtonName.FEATURE_B, ButtonName.FEAT_B}, { ButtonName.POSSESSION_B, ButtonName.SPELL_B}, { ButtonName.RETURN_TO_MENU }};
		}
	}

}