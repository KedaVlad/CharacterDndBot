package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.attacks;

import java.util.List;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat.Stats;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Ammunition.Ammunitions;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Weapon.WeaponProperties;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@JsonTypeName("ATTACK_MODIFICATION")
@Data
public class AttackModification implements ObjectDnd { 
	
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
}
