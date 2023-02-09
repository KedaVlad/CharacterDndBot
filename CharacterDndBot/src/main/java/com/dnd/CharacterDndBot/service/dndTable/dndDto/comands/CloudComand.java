package com.dnd.CharacterDndBot.service.dndTable.dndDto.comands;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@JsonTypeName("CLOUD_COMAND")
@Data
public class CloudComand implements InerComand
{
	private static final long serialVersionUID = 1L;
	protected String text;
	protected String name;

	public static CloudComand create(String name, String text)
	{
		CloudComand answer = new CloudComand();
		answer.name = name;
		answer.text = text;
		return answer;
	}
}
