package com.dnd.CharacterDndBot.service.dndTable.dndDto.comands;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@JsonTypeName("ADD_COMAND")
@Data
public class AddComand implements InerComand
{
	private static final long serialVersionUID = 1L;
	private ObjectDnd[] targets;
	
	public static AddComand create(ObjectDnd... object)
	{
		AddComand answer = new AddComand();
		answer.targets = object;
		return answer;
	}
	
}
