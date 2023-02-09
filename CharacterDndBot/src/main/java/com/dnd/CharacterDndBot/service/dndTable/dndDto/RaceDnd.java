package com.dnd.CharacterDndBot.service.dndTable.dndDto;

 
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;

import lombok.Data;

@Data
public class RaceDnd {

	private int speed;
	private String raceName;
	private String subRace;
	private InerComand[] specials;
}
