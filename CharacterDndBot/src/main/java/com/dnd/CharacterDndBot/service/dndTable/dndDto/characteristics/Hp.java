package com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.Refreshable;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;

import lombok.Data;

@Data
public class Hp implements Refreshable, ObjectDnd {

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
