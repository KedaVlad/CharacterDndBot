package com.dnd.CharacterDndBot.dnd.dto.comands;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("cloud_comand")
@Data
@EqualsAndHashCode(callSuper=false)
public class CloudComand extends InerComand {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String text;
	public static CloudComand create(String name, String text) {
		CloudComand answer = new CloudComand();
		answer.name = name;
		answer.text = text;
		return answer;
	}
}
