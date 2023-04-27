package app.dnd.util.math;

import app.dnd.model.enums.Roll;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class DamageDice extends Dice {

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
		StringBuilder answer = new StringBuilder(this.getName() + "[" + typeDamage.toString() + "]: " + roll());
		boolean start = true;
		for (int i = 0; i < getResults().length; i++) {
			int target = getResults()[i];
			if (start && (target != 0)) {
				answer.append(target);
				start = false;
			} else if (target < 0) {
				answer.append(" - ").append(target * -1);
			} else if (target > 0) {
				answer.append(" + ").append(target);
			}
		}
		return answer.append(")").toString();
	}

}
