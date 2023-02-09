package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.Refreshable;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.casts.Cast;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("ACTIVE_FEATURE")
@Data
@EqualsAndHashCode(callSuper = false)
public class ActiveFeature extends Feature implements Refreshable {
 
	private int charges;
	private int targetCells;
	private Time time;
	private Cast cast;

	@Override
	public void refresh(Time time) {
		if (this.time.equals(Time.SHORT) || time.equals(Time.LONG)) {
			targetCells = charges;
		}
	}
}
