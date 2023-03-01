package app.dnd.dto.ability.attacks;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import app.dnd.dto.characteristics.Stat.Stats;
import app.dnd.dto.stuffs.items.Ammunition.Ammunitions;
import app.dnd.dto.stuffs.items.Weapon.WeaponProperties;
import app.dnd.util.math.Dice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("attack_modification")
@Data
@EqualsAndHashCode(callSuper=false)
public class AttackModification extends ObjectDnd { 
	
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

	
	public static AttackModificationBuilder builder() {
		return new AttackModificationBuilder();
	}
	
	public String toString() {
		String answer = name + " |";
		for(WeaponProperties prop: requirement) {
			answer += prop.toString() + "|";
		}
		return answer;
	}
	
	public AttackModification marger(AttackModification second) {
		AttackModification answer = new AttackModification();
		answer.postAttack = second.postAttack;
		answer.name = this.name;
		answer.statDepend = this.statDepend;
		answer.requirement = this.requirement;
		answer.attack = this.attack;
		answer.attack.addAll(second.attack);
		answer.damage = this.damage;
		answer.damage.addAll(second.damage);
		answer.ammunition = this.ammunition;
		return answer;
	}
}
