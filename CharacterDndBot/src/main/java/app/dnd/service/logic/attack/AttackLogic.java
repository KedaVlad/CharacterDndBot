package app.dnd.service.logic.attack;

import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.model.ability.attacks.AttackAbility;
import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.model.enums.Proficiency;

public interface AttackLogic {

	public Proficiency prof(AttackModification attackModification, Long id);
	public void setAttack(AttackModification attackModification, Long id);
}

class AttacFacade implements AttackLogic {
	
	@Autowired
	public AttackProfBuilder attackProfBuilder;
	@Autowired
	private AttackAbilityService attackAbilityService;

	@Override
	public Proficiency prof(AttackModification attackModification, Long id) {
		return attackProfBuilder.build(attackModification, id);
	}

	@Override
	public void setAttack(AttackModification attackModification, Long id) {
		AttackAbility attackAbility = attackAbilityService.getById(id);
		attackAbility.setTargetAttack(attackModification);
		attackAbilityService.save(attackAbility);
	}
}