package app.dnd.dto.ability.spells;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.dto.ObjectDnd;
import app.dnd.dto.Refreshable;
import app.dnd.dto.characteristics.Stat.Stats;
import app.dnd.util.pools.Matrix;
import app.dnd.util.pools.SimplePool;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("magic_soul")
@Data
@EqualsAndHashCode(callSuper=false)
public class MagicSoul extends ObjectDnd implements Refreshable {
	
	private static final long serialVersionUID = 1L;
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
}
