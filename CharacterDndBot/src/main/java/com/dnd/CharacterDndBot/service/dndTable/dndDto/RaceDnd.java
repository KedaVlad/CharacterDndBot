package com.dnd.CharacterDndBot.service.dndTable.dndDto;

 
import org.springframework.data.mongodb.core.mapping.Document;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;

import lombok.Data;

@Data
@Document(collection = "dnd_objects.RaceDnd")
public class RaceDnd {

	private int speed;
	private String raceName;
	private String subRace;
	private InerComand[] specials;
}
