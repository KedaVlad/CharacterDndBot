package com.dnd.CharacterDndBot.dnd.util.math;

import com.dnd.CharacterDndBot.dnd.util.math.Formalizer.Roll;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DamageDice extends Dice {

	private static final long serialVersionUID = 1L;
	private TypeDamage typeDamage;

	public enum TypeDamage {
		CHOPPING, CRUSHING, STICKING
	}

	public DamageDice(String name, int buff, TypeDamage typeDamage, Roll... combo) {
		super(name, buff, combo);
		this.typeDamage = typeDamage;
	}

	public TypeDamage getTypeDamage() {
		return typeDamage;
	}

	public void setTypeDamage(TypeDamage typeDamage) {
		this.typeDamage = typeDamage;
	}

	public String execute() {
		String answer = this.getName() + "[" + typeDamage.toString() + "]: " + roll();
		boolean start = true;
		for (int i = 0; i < getResults().length; i++) {
			int target = getResults()[i];
			if (start && (target != 0)) {
				answer += "" + target;
				start = false;
			} else if (target < 0) {
				answer += " - " + target * -1;
			} else if (target > 0) {
				answer += " + " + target;
			} else {
				continue;
			}
		}
		return answer + ")";
	}

}
