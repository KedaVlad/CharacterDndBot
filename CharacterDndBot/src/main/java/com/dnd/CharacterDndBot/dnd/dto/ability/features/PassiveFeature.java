package com.dnd.CharacterDndBot.dnd.dto.ability.features;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("passive_feature")
@Data
@EqualsAndHashCode(callSuper=false)
public class PassiveFeature extends Feature {
  
	private static final long serialVersionUID = 1L;
	private String passive;

}
