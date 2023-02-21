package com.dnd.CharacterDndBot.dnd.dto.ability.features;

import com.dnd.CharacterDndBot.dnd.dto.Refreshable;
import com.dnd.CharacterDndBot.dnd.dto.ability.casts.Cast;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("active_feature")
@Data
@EqualsAndHashCode(callSuper = false)
public class ActiveFeature extends Feature implements Refreshable {

	private static final long serialVersionUID = 1L;
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
