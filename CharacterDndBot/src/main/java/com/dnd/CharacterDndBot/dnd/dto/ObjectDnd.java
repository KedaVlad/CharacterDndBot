package com.dnd.CharacterDndBot.dnd.dto;

import java.io.Serializable;

import com.dnd.CharacterDndBot.dnd.dto.ability.attacks.AttackModification;
import com.dnd.CharacterDndBot.dnd.dto.ability.features.Feature;
import com.dnd.CharacterDndBot.dnd.dto.ability.proficiency.Possession;
import com.dnd.CharacterDndBot.dnd.dto.ability.spells.MagicSoul;
import com.dnd.CharacterDndBot.dnd.dto.ability.spells.Spell;
import com.dnd.CharacterDndBot.dnd.dto.characteristics.Stat;
import com.dnd.CharacterDndBot.dnd.dto.characteristics.Stat.Stats;
import com.dnd.CharacterDndBot.dnd.dto.comands.InerComand;
import com.dnd.CharacterDndBot.dnd.dto.stuffs.items.Items;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "_class")
@JsonSubTypes({ @JsonSubTypes.Type(value = Items.class, name = "items"),
		@JsonSubTypes.Type(value = InerComand.class, name = "iner_comand"),
		@JsonSubTypes.Type(value = Feature.class, name = "feature"),
		@JsonSubTypes.Type(value = Possession.class, name = "possession"),
		@JsonSubTypes.Type(value = AttackModification.class, name = "attack_modification"),
		@JsonSubTypes.Type(value = Spell.class, name = "spell"),
		@JsonSubTypes.Type(value = MagicSoul.class, name = "magic_soul"),
		@JsonSubTypes.Type(value = Stats.class, name = "stats"),
        @JsonSubTypes.Type(value = Stat.class, name = "stat") })
public abstract class ObjectDnd implements Serializable, Informator {
	private static final long serialVersionUID = 1L;

}
