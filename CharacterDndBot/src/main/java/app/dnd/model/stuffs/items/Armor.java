package app.dnd.model.stuffs.items;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.enums.Armors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("armor")
@Data
@EqualsAndHashCode(callSuper = false)
public class Armor extends Items {

	private Armors type;

	public Armor() {}

	public Armor(Armors type) {
		this.setName(type.toString());
		this.setDescription(descripter(type));
		this.type = type;
	}

	private String descripter(Armors armor) {
		String answer = armor.toString() + "\n";
		answer += "Type armor: " + armor.getClazz() + "\n";
		if (armor.getRequitment() != 0) {
			answer += "You need " + armor.getRequitment() + " STRENGTH for using" + "\n";
		}
		if (armor.getStatDependBuff() != 0 && armor.getStatDependBuff() < 7) {
			answer += "Class Armor(CA): " + armor.getBaseArmor() + " + DEXTERITY(max " + armor.getStatDependBuff() + ")";
		} else if (armor.getStatDependBuff() != 0) {
			answer += "Class Armor(CA): " + armor.getBaseArmor() + " + DEXTERITY";
		} else {
			answer += "Class Armor(CA): " + armor.getBaseArmor();
		}

		return answer;
	}
	
}
