package app.dnd.dto.ability.attacks;

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
	
	private static final long serialVersionUID = 1L;
	private String name;
	private WeaponProperties[] requirement;
	private boolean permanent;
	private boolean postAttack;
	private boolean permanentCrit;
	private List<Dice> attack;
	private List<Dice> damage;
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
}
