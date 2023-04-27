package app.dnd.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.dnd.model.ability.Hp;
import app.dnd.model.ability.SaveRoll;
import app.dnd.model.ability.Skill;
import app.dnd.model.ability.Stat;
import app.dnd.model.commands.InnerCommand;
import app.dnd.model.stuffs.ItemInHand;
import app.dnd.model.stuffs.items.Items;
import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.model.telents.features.Feature;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.model.telents.spells.MagicSoul;
import app.dnd.model.telents.spells.Spell;


@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Items.class, name = "items"),
	    @JsonSubTypes.Type(value = ItemInHand.class, name = "item_in_hand"),
		@JsonSubTypes.Type(value = InnerCommand.class, name = "inner_command"),
		@JsonSubTypes.Type(value = Feature.class, name = "feature"),
		@JsonSubTypes.Type(value = Possession.class, name = "possession"),
		@JsonSubTypes.Type(value = AttackModification.class, name = "attack_modification"),
		@JsonSubTypes.Type(value = Spell.class, name = "spell"),
		@JsonSubTypes.Type(value = MagicSoul.class, name = "magic_soul"),
		@JsonSubTypes.Type(value = SaveRoll.class, name = "save_roll"),
		@JsonSubTypes.Type(value = Skill.class, name = "skill"),
		@JsonSubTypes.Type(value = Hp.class, name = "hp"),
        @JsonSubTypes.Type(value = Stat.class, name = "stat") })
public interface ObjectDnd {
}

