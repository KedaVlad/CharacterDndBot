package com.dnd.CharacterDndBot.service.dndTable.dndDto.stuffs.items;


import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "ITEM")
@JsonSubTypes({ @JsonSubTypes.Type(value = Armor.class, name = "ARMOR"),
		@JsonSubTypes.Type(value = Pack.class, name = "PACK"),
		@JsonSubTypes.Type(value = Pack.class, name = "AMMUNITION"),
		@JsonSubTypes.Type(value = Pack.class, name = "TOOL"),
		@JsonSubTypes.Type(value = Weapon.class, name = "WEAPON") })
@Data
public class Items implements ObjectDnd {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private boolean used;
}
