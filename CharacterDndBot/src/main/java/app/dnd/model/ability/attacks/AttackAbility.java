package app.dnd.model.ability.attacks;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.stuffs.items.Weapon;
import lombok.Data;

@Data
@Document(collection = "attack_ability")
public class AttackAbility {

	@Id
	private Long id;
	private Integer critX;
	private List<AttackModification> preAttacks;
	private List<AttackModification> afterAttak;
	private List<AttackModification> permanent;
	private Weapon targetWeapon;
	private AttackModification targetAttack;

	public static AttackAbility build(Long id) {
		AttackAbility attackAbility = new AttackAbility();
		attackAbility.critX = 1;
		attackAbility.afterAttak = new ArrayList<>();
		attackAbility.preAttacks = new ArrayList<>();
		attackAbility.permanent = new ArrayList<>();
		return attackAbility;
	}
}
