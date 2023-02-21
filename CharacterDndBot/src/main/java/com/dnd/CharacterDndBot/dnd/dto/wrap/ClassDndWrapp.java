package com.dnd.CharacterDndBot.dnd.dto.wrap;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.dnd.CharacterDndBot.dnd.dto.comands.InerComand;
import com.dnd.CharacterDndBot.dnd.util.math.Formalizer.Roll;

import lombok.Data;

@Data
@Document(collection = "classes")
public class ClassDndWrapp {
	
	@Id
	private String id;
	public String className;
	public String archetype;
	public String information;
	public Roll diceHp;
	public int firstHp;
	@Field("iner_comand")
	public InerComand[][] growMap;

}
