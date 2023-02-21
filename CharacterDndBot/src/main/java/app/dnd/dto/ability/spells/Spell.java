package app.dnd.dto.ability.spells;
 
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import app.dnd.dto.ability.casts.Cast;
import app.dnd.service.factory.SpellFactory.SpellClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("spell")
@Data
@EqualsAndHashCode(callSuper=false)
public class Spell extends ObjectDnd {
	 
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
