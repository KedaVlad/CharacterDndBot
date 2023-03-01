package app.dnd.dto.ability.attacks;

import java.util.ArrayList;
import java.util.List;

import app.dnd.dto.characteristics.Stat.Stats;
import app.dnd.dto.stuffs.items.Ammunition.Ammunitions;
import app.dnd.dto.stuffs.items.Weapon.WeaponProperties;
import app.dnd.util.math.Dice;

public class AttackModificationBuilder {
	
	private String name;
	private WeaponProperties[] requirement;
	private boolean permanent;
	private boolean postAttack;
	private boolean permanentCrit;
	private List<Dice> attack = new ArrayList<>();
	private List<Dice> damage = new ArrayList<>();
	private Ammunitions ammunition;
	private Stats statDepend;
	private Stats secondStat;

	public AttackModification build() {
		AttackModification answer = new AttackModification();
		answer.setName(name);
		answer.setRequirement(requirement);
		answer.setPermanent(permanent);
		answer.setPostAttack(postAttack);
		answer.setPermanentCrit(permanentCrit);
		answer.setAttack(attack);
		answer.setDamage(damage);
		answer.setAmmunition(ammunition);
		answer.setStatDepend(statDepend);
		answer.setSecondStat(secondStat);
		return answer;
	}

	public AttackModificationBuilder name(String name) {
		this.name = name;
		return this;
	}

	public AttackModificationBuilder requirement(WeaponProperties... requirement) {
		this.requirement = requirement;
		return this;
	}

	public AttackModificationBuilder permanent() {
		this.permanent = true;
		this.postAttack = false;
		return this;
	}

	public AttackModificationBuilder postAttack() {
		this.permanent = false;
		this.postAttack = true;
		return this;
	}

	public AttackModificationBuilder permanentCrit() {
		this.permanentCrit = true;
		return this;
	}

	public AttackModificationBuilder addAttack(Dice dice) {
		this.attack.add(dice);
		return this;
	}

	public AttackModificationBuilder damage(Dice dice) {
		this.damage.add(dice);
		return this;
	}

	public AttackModificationBuilder ammunition(Ammunitions ammunition) {
		this.ammunition = ammunition;
		return this;
	}

	public AttackModificationBuilder mainStat(Stats statDepend) {
		this.statDepend = statDepend;
		return this;
	}

	public AttackModificationBuilder secondStat(Stats secondStat) {
		this.secondStat = secondStat;
		return this;
	}

}
