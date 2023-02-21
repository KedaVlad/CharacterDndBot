package com.dnd.CharacterDndBot.dnd.dto.ability.features;

import com.dnd.CharacterDndBot.dnd.dto.ability.attacks.AttackModification;
import com.dnd.CharacterDndBot.dnd.util.math.Dice;
import com.dnd.CharacterDndBot.dnd.util.pools.Matrix;
import com.dnd.CharacterDndBot.dnd.util.pools.SimplePool;

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
