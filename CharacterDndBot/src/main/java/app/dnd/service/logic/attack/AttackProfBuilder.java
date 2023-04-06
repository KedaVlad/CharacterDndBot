package app.dnd.service.logic.attack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.model.ability.proficiency.Proficiencies;
import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.WeaponProperties;
import app.dnd.service.logic.talants.prof.ProficienciesService;

@Component
class AttackProfBuilder {

	@Autowired
	private ProficienciesService proficienciesService;
	@Autowired
	private AttackAbilityService attackAbilityService;
	
	public Proficiency build(AttackModification attackModification, Long id) {
		
		Proficiencies proficiencies = proficienciesService.getById(id);

		if (proficiencies.checkProficiency("Military Weapon") //WeaponProperties.MILITARY.toString())
				|| (simpleCheck(attackModification) && proficiencies.checkProficiency("Simple Weapon")) //WeaponProperties.SIMPLE.toString()))
				|| proficiencies.checkProficiency(attackAbilityService.getById(id).getTargetWeapon().getType().toString())) {
			return Proficiency.BASE;
		}
		return null;
	}

	private boolean simpleCheck(AttackModification attackModification) {
		for (WeaponProperties properties : attackModification.getRequirement()) {
			if (properties.equals(WeaponProperties.SIMPLE))
				return true;
		}
		return false;
	}
}
