package app.dnd.service.logic.talants.feature;

import app.dnd.model.ability.Ability;
import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.model.ability.spells.MagicSoul;

public interface AbilityLogic {
	
	public Ability getAbility(Long id);
	public MagicSoul getMagicSoul(Long id);
	public Proficiencies getProficiencies(Long id);
	public String[][] abilityButtons(Long id);
	
}
