package com.dnd.CharacterDndBot.service.dndTable.dndDto.ability.features;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("INER_FEATURE")
@Data
@EqualsAndHashCode(callSuper = false)
public class InerFeature extends Feature { 
	private static final long serialVersionUID = 1L;
	private InerComand[] comand;
}
