package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.attacks;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Weapon;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer.Roll;

import lombok.Data;

@Data
@Component
public class AttackAbility {

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
