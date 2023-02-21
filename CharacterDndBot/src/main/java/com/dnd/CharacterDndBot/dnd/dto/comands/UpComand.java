package com.dnd.CharacterDndBot.dnd.dto.comands;

import com.dnd.CharacterDndBot.dnd.dto.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("up_comand")
@Data
@EqualsAndHashCode(callSuper=false)
public class UpComand extends InerComand {
	private static final long serialVersionUID = 1L;
	private ObjectDnd object;
	private int value;

}
