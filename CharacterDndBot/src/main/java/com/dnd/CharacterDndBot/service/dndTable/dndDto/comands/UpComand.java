package com.dnd.CharacterDndBot.service.dndTable.dndDto.comands;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@JsonTypeName("UP_COMAND")
@Data
public class UpComand implements InerComand 
{
	private static final long serialVersionUID = 1L;
	private String name;
	private long key;
	private int value;
	
	public static UpComand create(String name, long key, int value)
	{
		UpComand comand = new UpComand();
		comand.name =name;
		comand.key = key;
		comand.value = value;
		return comand;
	}
}
