package app.dnd.dto.ability.attacks;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.dnd.dto.stuffs.items.Weapon;
import app.dnd.util.math.Dice;
import app.dnd.util.math.Formalizer.Roll;
import lombok.Data;

@Data
public class AttackAbility implements Serializable{

	private static final long serialVersionUID = 1L;
	private Dice noWeapon;
	private Integer critX;
	private List<AttackModification> preAttacks;
	private List<AttackModification> afterAttak;
	private List<AttackModification> permanent;
	private Weapon targetWeapon;
	private AttackModification targetAttack;

	{
		noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);
		critX = 1;
		preAttacks = new ArrayList<>();
		afterAttak = new ArrayList<>();
		permanent = new ArrayList<>();
	}
}
