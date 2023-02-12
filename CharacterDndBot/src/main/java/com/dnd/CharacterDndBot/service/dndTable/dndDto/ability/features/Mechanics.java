package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.attacks.AttackModification;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;
import com.dnd.CharacterDndBot.service.dndTable.dndPools.Matrix;
import com.dnd.CharacterDndBot.service.dndTable.dndPools.SimplePool;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Mechanics extends Feature {
 
	private static final long serialVersionUID = 1L;
	private SimplePool<Feature> pool;
	private Matrix matrix;
	private Dice dice;
	private long key;
	private AttackModification[] modification;

}
