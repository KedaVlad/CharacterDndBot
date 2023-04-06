package app.dnd.model.ability.spells;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import app.dnd.model.Refreshable;
import app.dnd.model.enums.Stats;
import app.dnd.util.pools.Matrix;
import app.dnd.util.pools.SimplePool;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "magic_soul")
@JsonTypeName("magic_soul")
public class MagicSoul implements ObjectDnd, Refreshable {
	
	@Id
	private Long id;
	private Matrix cells;
	private SimplePool<Spell> poolCantrips;
	private Stats depends;
	private SimplePool<Spell> poolSpells;
	private Spell targetSpell;
	private Time time;

	@Override
	public void refresh(Time time) {
		if (this.time == Time.SHORT || time == Time.LONG) {
			cells.refresh();
		}
	}

	public static MagicSoul build(Long id) {
		MagicSoul magicSoul = new MagicSoul();
		magicSoul.id = id;
		return magicSoul;
	}
}
