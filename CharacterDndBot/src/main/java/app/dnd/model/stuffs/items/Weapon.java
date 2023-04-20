package app.dnd.model.stuffs.items;


import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.enums.Weapons;
import app.dnd.model.telents.attacks.AttackModification;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("weapon")
@Data
@EqualsAndHashCode(callSuper=false)
public class Weapon extends Items {
 
	private int attack;
	private int damage;
	private Weapons type;

	public Weapon() {
	}

	public Weapon(Weapons type) {
		this.setName(type.getName());
		this.setDescription(descripter(type));
		this.type = type;
	}

	private String descripter(Weapons type) {
		String answer = type.getName() + "\n";
		for (AttackModification typeAttack : type.getAttackTypes()) {
			answer += typeAttack.toString() + "\n";
		}
		return answer;
	}

	public String toString() {
		if (attack != 0 || damage != 0) {
			return getName() + "(" + attack + "|" + damage + ")";
		}
		return getName();
	}
}
