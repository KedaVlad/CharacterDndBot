package com.dnd.CharacterDndBot.service.dndTable.dndDto;
 
import org.springframework.data.mongodb.core.mapping.Document;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.comands.InerComand;
import com.dnd.CharacterDndBot.service.dndTable.dndMath.Formalizer.Roll;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "CLASS_DND")
@Document(collection = "dnd_objects.ClassDnd")
@Data
public class ClassDnd implements ObjectDnd { 
	
	private static final long serialVersionUID = 1L;
	private String className;
	private String archetype;
	@JsonIgnore
	private int lvl;
	private Roll diceHp;
	private int firstHp;
	private InerComand[][] growMap;

}
