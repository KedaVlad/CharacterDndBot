package app.dnd.model.wrapp;


import app.dnd.model.enums.Roll;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.dnd.model.commands.InnerCommand;
import lombok.Data;

@Data
@Document(collection = "classes_test")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME, property = "type")
public class ClassDndWrapp {
	
	@Id
	private String id;
	public String className;
	public String archetype;
	public String information;
	public Roll diceHp;
	public int firstHp;
	public InnerCommand[][] growMap;

}
