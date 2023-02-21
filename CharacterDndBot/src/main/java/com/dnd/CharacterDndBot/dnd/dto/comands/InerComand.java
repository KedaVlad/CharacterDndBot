package com.dnd.CharacterDndBot.dnd.dto.comands;

import com.dnd.CharacterDndBot.dnd.dto.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.EXTERNAL_PROPERTY, use = JsonTypeInfo.Id.NAME,  property = "iner_comand")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AddComand.class, name = "add_comand"),
	@JsonSubTypes.Type(value = UpComand.class, name = "up_comand"),
	@JsonSubTypes.Type(value = CloudComand.class, name = "cloud_comand")})

public abstract class InerComand extends ObjectDnd {
	private static final long serialVersionUID = 1L;
	
}
