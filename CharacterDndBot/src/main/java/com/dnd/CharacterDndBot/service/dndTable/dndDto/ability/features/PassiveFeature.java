package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("PASSIVE_FEATURE")
@Data
@EqualsAndHashCode(callSuper=false)
public class PassiveFeature extends Feature {
  
	private static final long serialVersionUID = 1L;
	private String passive;

}
