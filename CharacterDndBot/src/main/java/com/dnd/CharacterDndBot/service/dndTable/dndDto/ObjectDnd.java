package com.dnd.CharacterDndBot.service.dndTable.dndDto;

import java.io.Serializable;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.attacks.AttackModification;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features.Feature;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.spells.MagicSoul;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.spells.Spell;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items.Items;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "OBJECT_DND")
@JsonSubTypes({ @JsonSubTypes.Type(value = Items.class, name = "ITEM"),
		@JsonSubTypes.Type(value = InerComand.class, name = "COMAND"),
		@JsonSubTypes.Type(value = Feature.class, name = "FEATURE"),
		@JsonSubTypes.Type(value = Possession.class, name = "POSSESSION"),
		@JsonSubTypes.Type(value = AttackModification.class, name = "ATTACK_MODIFICATION"),
		@JsonSubTypes.Type(value = Spell.class, name = "SPELL"),
		@JsonSubTypes.Type(value = MagicSoul.class, name = "MAGIC_SOUL") })
public interface ObjectDnd extends Serializable {

}
