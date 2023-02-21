package com.dnd.CharacterDndBot.dnd.dto.ability.features;

import com.dnd.CharacterDndBot.dnd.dto.comands.InerComand;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("iner_feature")
@Data
@EqualsAndHashCode(callSuper = false)
public class InerFeature extends Feature { 
	private static final long serialVersionUID = 1L;
	private InerComand[] comand;
	
	public static InerFeature create(String string, String string2, InerComand... create) {

		InerFeature answer = new InerFeature();
		answer.setName(string);
		answer.setDescription(string2);
		answer.comand = create;
		
		return answer;
	}
}
