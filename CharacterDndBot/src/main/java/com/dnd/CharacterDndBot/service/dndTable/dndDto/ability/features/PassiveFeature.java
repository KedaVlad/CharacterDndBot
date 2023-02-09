package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("PASSIVE_FEATURE")
public class PassiveFeature extends Feature {
  
	private String passive;

	public static PassiveFeature create(String name, String text, String passive) {
		PassiveFeature answer = new PassiveFeature();
		answer.setName(name);
		answer.setDescription(text);
		answer.passive = passive;
		return answer;
	}

	public String getPassive() {
		return passive;
	}

}
