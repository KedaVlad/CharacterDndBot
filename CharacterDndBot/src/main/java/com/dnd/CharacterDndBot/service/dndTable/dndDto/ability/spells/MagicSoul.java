package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.spells;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.Refreshable;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics.Stat.Stats;
import com.dnd.CharacterDndBot.service.dndTable.dndPools.Matrix;
import com.dnd.CharacterDndBot.service.dndTable.dndPools.SimplePool;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@JsonTypeName("MAGIC_SOUL")
@Data
public class MagicSoul implements Refreshable, ObjectDnd {
	
	private static final long serialVersionUID = 1L;
	private Matrix cells;
	private SimplePool<Spell> poolCantrips;
	private Stats depends;
	private SimplePool<Spell> pool;
	private Spell targetSpell;
	private Time time;

	@Override
	public void refresh(Time time) {
		if (this.time == Time.SHORT || time == Time.LONG) {
			cells.refresh();
		}
	}
}
