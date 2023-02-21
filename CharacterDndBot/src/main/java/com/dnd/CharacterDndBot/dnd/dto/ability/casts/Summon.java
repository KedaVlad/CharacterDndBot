package com.dnd.CharacterDndBot.dnd.dto.ability.casts;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Summon extends Cast {
	private String text;
}
