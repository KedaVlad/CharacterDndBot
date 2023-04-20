package app.dnd.model.telents.spells;
 
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import app.dnd.model.telents.casts.Cast;
import app.player.service.stage.event.factory.SpellFactory.SpellClass;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("spell")
public class Spell implements ObjectDnd {
	
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
