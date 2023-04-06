package app.dnd.service.logic.attack;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.model.enums.WeaponProperties;

@Component
class SubAttackBuilder {

	public List<AttackModification> build(AttackModification attack, List<AttackModification> attacks) {
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
}
