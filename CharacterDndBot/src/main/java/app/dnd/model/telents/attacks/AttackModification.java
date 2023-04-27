package app.dnd.model.telents.attacks;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Objects;

import app.dnd.model.ObjectDnd;
import app.dnd.model.enums.Ammunitions;
import app.dnd.model.enums.Stats;
import app.dnd.model.enums.WeaponProperties;
import app.dnd.util.math.Dice;
import lombok.Data;

@JsonTypeName("attack_modification")
@Data
public class AttackModification implements ObjectDnd { 
	
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
		StringBuilder answer = new StringBuilder(name + " |");
		for(WeaponProperties prop: requirement) {
			answer.append(prop.toString()).append("|");
		}
		return answer.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == this) return true;
		if(!(object instanceof AttackModification other)) return false;
		return Objects.equal(this.name, other.name) &&  Objects.equal(this.requirement, other.requirement);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(name, requirement);
	}
	
	public AttackModification merger(AttackModification second) {
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
