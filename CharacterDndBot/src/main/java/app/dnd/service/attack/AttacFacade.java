package app.dnd.service.attack;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.enums.Proficiency;
import app.dnd.model.enums.WeaponProperties;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.model.telents.attacks.AttackAbility;
import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.model.telents.proficiency.Proficiencies;
import app.dnd.service.talants.ProficienciesService;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;
import app.user.model.ActualHero;

@Component
public class AttacFacade implements AttackLogic {
	
	@Autowired
	public ProficienciesService proficienciesService;
	@Autowired
	private AttackAbilityService attackAbilityService;

	@Override
	public Proficiency prof(ActualHero hero, AttackModification attackModification) {
		Proficiencies proficiencies = proficienciesService.findByIdAndOwnerName(hero.getId(), hero.getName());

		if (proficiencies.checkProficiency("Military Weapon") //WeaponProperties.MILITARY.toString())
				|| (simpleCheck(attackModification) && proficiencies.checkProficiency("Simple Weapon")) //WeaponProperties.SIMPLE.toString()))
				|| proficiencies.checkProficiency(attackAbilityService.findByIdAndOwnerName(hero.getId(), hero.getName()).getTargetWeapon().getType().toString())) {
			return Proficiency.BASE;
		}
		return null;
	}
		
	@Override
	public void setAttack(ActualHero hero, AttackModification attackModification) {
		AttackAbility attackAbility = attackAbilityService.findByIdAndOwnerName(hero.getId(), hero.getName());
		attackAbility.setTargetAttack(attackModification);
		attackAbilityService.save(attackAbility);
	}
	
	@Override
	public List<AttackModification> hit(ActualHero hero) {
		AttackAbility attackAbility = attackAbilityService.findByIdAndOwnerName(hero.getId(), hero.getName());
		AttackModification attack = attackAbility.getTargetAttack();
		return subAttackBuild(attack, attackAbility.getAfterAttak());
	}

	@Override
	public List<AttackModification> buildAttack(ActualHero hero, Weapon weapon) {
		AttackAbility attackAbility = attackAbilityService.findByIdAndOwnerName(hero.getId(), hero.getName());
		List<AttackModification> answer = new ArrayList<>();
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
			answer.addAll(subAttackBuild(target, attackAbility.getPreAttacks()));

		}
		return answer;
	}
	
	@Override
	public List<AttackModification> crit(ActualHero hero) {
		AttackAbility attackAbility = attackAbilityService.findByIdAndOwnerName(hero.getId(), hero.getName());
		AttackModification attack = attackAbility.getTargetAttack();
		return subAttackBuild(crit(attackAbility, attack), attackAbility.getAfterAttak());
		
	}
	
	private List<AttackModification> subAttackBuild(AttackModification attack, List<AttackModification> attacks) {
		List<AttackModification> answer = new ArrayList<>();
		answer.add(attack);
		for (AttackModification type : attacks) {
			int condition = type.getRequirement().length;
			String typeAttak = "|";
			for (WeaponProperties properties : type.getRequirement()) {
				typeAttak += properties.toString() + "|";
				for (WeaponProperties need : attack.getRequirement()) {
					if (properties.equals(need)) {
						condition--;
						break;
					}
				}
			}
			if (condition == 0) {
				AttackModification compleat = attack.marger(type);
				compleat.setName(type.getName() + typeAttak);
				answer.add(compleat);
			}
		}

		return answer;
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
	
	private boolean simpleCheck(AttackModification attackModification) {
		for (WeaponProperties properties : attackModification.getRequirement()) {
			if (properties.equals(WeaponProperties.SIMPLE))
				return true;
		}
		return false;
	}

	

	
	
}
