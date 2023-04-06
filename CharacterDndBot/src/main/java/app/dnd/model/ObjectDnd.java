package app.dnd.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.dnd.model.ability.attacks.AttackModification;
import app.dnd.model.ability.features.Feature;
import app.dnd.model.ability.proficiency.Possession;
import app.dnd.model.ability.spells.MagicSoul;
import app.dnd.model.ability.spells.Spell;
import app.dnd.model.characteristics.Hp;
import app.dnd.model.characteristics.SaveRoll;
import app.dnd.model.characteristics.Skill;
import app.dnd.model.characteristics.Stat;
import app.dnd.model.comands.InerComand;
import app.dnd.model.stuffs.items.Items;


@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Items.class, name = "items"),
		@JsonSubTypes.Type(value = InerComand.class, name = "iner_comand"),
		@JsonSubTypes.Type(value = Feature.class, name = "feature"),
		@JsonSubTypes.Type(value = Possession.class, name = "possession"),
		@JsonSubTypes.Type(value = AttackModification.class, name = "attack_modification"),
		@JsonSubTypes.Type(value = Spell.class, name = "spell_wrapp"),
		@JsonSubTypes.Type(value = MagicSoul.class, name = "magic_soul"),
		@JsonSubTypes.Type(value = SaveRoll.class, name = "save_roll"),
		@JsonSubTypes.Type(value = Skill.class, name = "skill"),
		@JsonSubTypes.Type(value = Hp.class, name = "hp"),
        @JsonSubTypes.Type(value = Stat.class, name = "stat") })
public interface ObjectDnd {
}

