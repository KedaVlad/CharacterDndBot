package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.casts;

import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Damage extends Cast {
	private Dice[] damage;
}
