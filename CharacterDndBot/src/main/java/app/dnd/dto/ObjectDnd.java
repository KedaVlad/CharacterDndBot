package app.dnd.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.dnd.dto.ability.attacks.AttackModification;
import app.dnd.dto.ability.features.Feature;
import app.dnd.dto.ability.proficiency.Possession;
import app.dnd.dto.ability.spells.MagicSoul;
import app.dnd.dto.ability.spells.Spell;
import app.dnd.dto.characteristics.Hp;
import app.dnd.dto.characteristics.Skill;
import app.dnd.dto.characteristics.Stat;
import app.dnd.dto.comands.InerComand;
import app.dnd.dto.stuffs.items.Items;
import lombok.Data;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "_class")
@JsonSubTypes({ @JsonSubTypes.Type(value = Items.class, name = "items"),
		@JsonSubTypes.Type(value = InerComand.class, name = "iner_comand"),
		@JsonSubTypes.Type(value = Feature.class, name = "feature"),
		@JsonSubTypes.Type(value = Possession.class, name = "possession"),
		@JsonSubTypes.Type(value = AttackModification.class, name = "attack_modification"),
		@JsonSubTypes.Type(value = Spell.class, name = "spell"),
		@JsonSubTypes.Type(value = MagicSoul.class, name = "magic_soul"),
		@JsonSubTypes.Type(value = Skill.class, name = "skill"),
		@JsonSubTypes.Type(value = Hp.class, name = "hp"),
        @JsonSubTypes.Type(value = Stat.class, name = "stat") })
@Data
public abstract class ObjectDnd implements Informator {
	
	private String privateKey = UUID.randomUUID().toString();
}
