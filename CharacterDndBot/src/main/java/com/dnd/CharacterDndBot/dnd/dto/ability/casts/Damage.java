package com.dnd.CharacterDndBot.dnd.dto.ability.casts;

import com.dnd.CharacterDndBot.dnd.util.math.Dice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Damage extends Cast {
	private Dice[] damage;
}
