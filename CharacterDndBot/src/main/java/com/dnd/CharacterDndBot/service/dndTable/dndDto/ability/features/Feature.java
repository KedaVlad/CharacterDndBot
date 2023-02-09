package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "FEATURE")
@JsonSubTypes({ @JsonSubTypes.Type(value = ActiveFeature.class, name = "ACTIVE_FEATURE"),
		@JsonSubTypes.Type(value = InerFeature.class, name = "INER_FEATURE"),
		@JsonSubTypes.Type(value = PassiveFeature.class, name = "PASSIVE_FEATURE") })
@Data
public class Feature implements ObjectDnd {
 
	private String name;
	private String description;

	public String toString() {
		return getName() + " - " + getDescription();
	}

}
