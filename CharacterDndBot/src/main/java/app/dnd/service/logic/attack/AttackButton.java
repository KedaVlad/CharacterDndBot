package app.dnd.service.logic.attack;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.attacks.AttackAbility;
import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.RollAction;
import app.dnd.model.enums.WeaponProperties;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;
import app.player.model.enums.Location;

public interface AttackButton {

	public BaseAction[][] preAttak(Weapon weapon, Long id);
	public BaseAction[][] hit(Long id);
	public BaseAction[][] crit(Long id);
}

@Component
class AttackButtonBuilder implements AttackButton {

	@Autowired
	private PreAttakButtonBuilder preAttakButtonBuilder;
	@Autowired
	private HitButtonBuilder hitButtonBuilder;
	@Autowired
	private CritButtonBuilder critButtonBuilder;

	@Override
	public BaseAction[][] preAttak(Weapon weapon, Long id) {
		return preAttakButtonBuilder.build(weapon, id);
	}

	@Override
	public BaseAction[][] hit(Long id) {
		return hitButtonBuilder.build(id);
	}

	@Override
	public BaseAction[][] crit(Long id) {
		return critButtonBuilder.build(id);
	}

}

@Component
class PreAttakButtonBuilder {

	@Autowired
	private SubAttackBuilder subAttackBuilder;
	@Autowired 
	private AttackAbilityService attackAbilityService;

	public BaseAction[][] build(Weapon weapon, Long id) {
		AttackAbility attackAbility = attackAbilityService.getById(id);
		attackAbility.setTargetWeapon(weapon); 
		List<AttackModification> attacks = buildAttack(attackAbility);
		BaseAction[][] pool = new BaseAction[attacks.size()][1];
		for (int i = 0; i < attacks.size(); i++) {
			AttackModification target = attacks.get(i);
			pool[i][0] = Action.builder().location(Location.ATTACK_MACHINE).name(target.getName()).objectDnd(target).build();
		}
		return pool;
	}


	private List<AttackModification> buildAttack(AttackAbility attackAbility) {
		List<AttackModification> answer = new ArrayList<>();
		Weapon weapon = attackAbility.getTargetWeapon();
		AttackModification base = new AttackModification();
		base.setName("Base attack");
		if (weapon.getAttack() > 0) {
			base.getAttack().add(new Dice("Weapon buff", weapon.getAttack(), Roll.NO_ROLL));
		}
		if (weapon.getDamage() > 0) {
			base.getAttack().add(new Dice("Weapon buff", weapon.getDamage(), Roll.NO_ROLL));
		}
		for (AttackModification type : weapon.getType().getAttackTypes()) {
			AttackModification target = type.marger(base);
			target = permanentBuff(attackAbility, target);
			answer.addAll(subAttackBuilder.build(target, attackAbility.getPreAttacks()));

		}
		return answer;

	}

	private AttackModification permanentBuff(AttackAbility attackAbility, AttackModification attack) {
		for (AttackModification type : attackAbility.getPermanent()) {
			int condition = type.getRequirement().length;
			for (WeaponProperties properties : type.getRequirement()) {
				if (condition == 0) {
					attack = attack.marger(type);
					break;
				} else {
					for (WeaponProperties need : attack.getRequirement()) {
						if (properties.equals(need)) {
							condition--;
							break;
						}
					}
				}
			}
		}
		return attack;
	}
}

@Component
class HitButtonBuilder {

	@Autowired
	private AttackAbilityService attackAbilityService;
	@Autowired
	private SubAttackBuilder subAttackBuilder;

	public BaseAction[][] build(Long id) {
		AttackAbility attackAbility = attackAbilityService.getById(id);
		AttackModification attack = attackAbility.getTargetAttack();
		List<AttackModification> attacks = subAttackBuilder.build(attack, attackAbility.getAfterAttak());
		BaseAction[][] nextSteps = new BaseAction[attacks.size() + 1][1];
		for (int i = 0; i < attacks.size(); i++) {
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.buider().name(target.getName())
					.diceCombo(target.getDamage().toArray(Dice[]::new)).statDepend(target.getStatDepend()).build();
		}
		nextSteps[nextSteps.length - 1][0] = Action.builder().location(Location.ATTACK_MACHINE).name("MISS").build();
		return nextSteps;
	}
}

@Component
class CritButtonBuilder {

	@Autowired
	private AttackAbilityService attackAbilityService;
	@Autowired
	private SubAttackBuilder subAttackBuilder;

	public BaseAction[][] build(Long id) {

		AttackAbility attackAbility = attackAbilityService.getById(id);
		AttackModification attack = attackAbility.getTargetAttack();
		List<AttackModification> attacks = subAttackBuilder.build(crit(attackAbility, attack), attackAbility.getAfterAttak());
		BaseAction[][] nextSteps = new BaseAction[attacks.size()][1];
		for (int i = 0; i < attacks.size(); i++) {
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.buider().name(target.getName())
					.diceCombo((Dice[]) target.getDamage().toArray()).statDepend(target.getStatDepend()).build();
		}

		return nextSteps;
	}

	private AttackModification crit(AttackAbility attackAbility, AttackModification attack) {
		Dice damage = attack.getDamage().get(0);
		Roll roll = damage.getCombo()[0];
		List<Roll> critsRolls = new ArrayList<>();
		for (int i = 0; i < attackAbility.getCritX(); i++) {
			critsRolls.add(roll);
		}
		attack.getDamage().add(new Dice("Crit", 0, (Roll[]) critsRolls.toArray()));
		return attack;
	}
}
