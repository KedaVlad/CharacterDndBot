package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.spells;
 
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.casts.Cast;
import com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService.SpellFactory.SpellClass;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@JsonTypeName("SPELL")
@Data
public class Spell implements ObjectDnd {
	 
	private static final long serialVersionUID = 1L;
	private int lvlSpell;
	private Cast cast;
	private String name;
	private String description;
	private String applicationTime;
	private String distanse;
	private String duration;
	private SpellClass[] classFor;
	private boolean concentration;
}
