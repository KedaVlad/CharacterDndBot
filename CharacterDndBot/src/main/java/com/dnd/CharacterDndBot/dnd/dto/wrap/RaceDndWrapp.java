package com.dnd.CharacterDndBot.dnd.dto.wrap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.dnd.CharacterDndBot.dnd.dto.comands.InerComand;

import lombok.Data;

@Data
@Document(collection = "races")
public class RaceDndWrapp {
	
	@Id
	private String id;
	private String information;
	private String raceName;
	private String subRace;
	private int speed;
	@Field("iner_comand")
	private InerComand[] specials;
}
