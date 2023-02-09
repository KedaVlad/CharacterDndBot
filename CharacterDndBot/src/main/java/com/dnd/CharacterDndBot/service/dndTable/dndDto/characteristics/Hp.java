package com.dnd.CharacterDndBot.service.dndTable.dndDto.characteristics;

  
import org.springframework.stereotype.Component;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.Refreshable;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Dice;

import lombok.Data;

@Data
@Component
public class Hp implements Refreshable {

	private int max = 0;
	private int now = 0;
	private int timeHp = 0;
	private boolean cknoked = false;
	private boolean dead = false;
	private Dice hpDice;

	@Override
	public void refresh(Time time) {
		if (time == Time.LONG) {
			now = max;
		}
	}
}
