package com.dnd.CharacterDndBot.dnd.dto.ability.attacks;

import java.util.List;

import com.dnd.CharacterDndBot.dnd.dto.ObjectDnd;
import com.dnd.CharacterDndBot.dnd.dto.characteristics.Stat.Stats;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Ammunition.Ammunitions;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Weapon.WeaponProperties;
import com.dnd.CharacterDndBot.dnd.util.math.Dice;
import com.fasterxml.jackson.annotation.JsonTypeName;

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
