package com.dnd.CharacterDndBot.dnd.dto.characteristics;

import java.io.Serializable;

import com.dnd.CharacterDndBot.dnd.dto.Informator;
import com.dnd.CharacterDndBot.dnd.dto.Refreshable;

import lombok.Data;

@Data
public class Hp implements Refreshable, Informator, Serializable {

	private static final long serialVersionUID = 1L;
	private int max = 0;
	private int now = 0;
	private int timeHp = 0;
	private boolean cknoked = false;
	private boolean dead = false;
	private int hpBonus  = 0;

	@Override
	public void refresh(Time time) {
		if (time == Time.LONG) {
			now = max;
		}
	}
}
