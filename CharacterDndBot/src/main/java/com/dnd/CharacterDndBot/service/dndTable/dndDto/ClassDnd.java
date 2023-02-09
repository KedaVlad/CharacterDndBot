package com.dnd.CharacterDndBot.service.dndTable.dndDto;
 
import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer.Roll;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "CLASS_DND")
@Data
public class ClassDnd { 
	
	private String className;
	private String myArchetypeClass;
	@JsonIgnore
	private int lvl;
	private Roll diceHp;
	private int firstHp;
	private InerComand[][] growMap;

}
