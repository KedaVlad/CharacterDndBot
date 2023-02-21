package com.dnd.CharacterDndBot.dnd.dto.ability.spells;

import com.dnd.CharacterDndBot.dnd.dto.ObjectDnd;
import com.dnd.CharacterDndBot.dnd.dto.Refreshable;
import com.dnd.CharacterDndBot.dnd.dto.characteristics.Stat.Stats;
import com.dnd.CharacterDndBot.dnd.util.pools.Matrix;
import com.dnd.CharacterDndBot.dnd.util.pools.SimplePool;
import com.fasterxml.jackson.annotation.JsonTypeName;

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
