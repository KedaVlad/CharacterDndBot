package com.dnd.CharacterDndBot.service.dndTable.dndDto.comands;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "COMAND")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AddComand.class, name = "ADD_COMAND"),
	@JsonSubTypes.Type(value = UpComand.class, name = "UP_COMAND"),
	@JsonSubTypes.Type(value = CloudComand.class, name = "CLOUD_COMAND")})

public interface InerComand extends ObjectDnd 
{
	
}
